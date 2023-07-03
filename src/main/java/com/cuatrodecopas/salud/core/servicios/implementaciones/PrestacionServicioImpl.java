package com.cuatrodecopas.salud.core.servicios.implementaciones;

import com.cuatrodecopas.salud.core.entidades.Prestacion;
import com.cuatrodecopas.salud.core.entidades.Profesional;
import com.cuatrodecopas.salud.core.entidades.Usuario;
import com.cuatrodecopas.salud.core.servicios.interfaces.CalificacionServicio;
import com.cuatrodecopas.salud.core.servicios.interfaces.PrestacionServicio;
import com.cuatrodecopas.salud.core.servicios.interfaces.ProfesionalServicio;
import com.cuatrodecopas.salud.core.servicios.interfaces.UsuarioServicio;
import com.cuatrodecopas.salud.data.repositorios.PrestacionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.cuatrodecopas.salud.configuracion.Configuraciones.obtenerFechaActual;

@Service
public class PrestacionServicioImpl implements PrestacionServicio {
    @Autowired
    PrestacionRepositorio prestacionRepositorio;

    @Autowired
    private CalificacionServicio calificacionServicio;

    @Autowired
    private ProfesionalServicio profesionalServicio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Override
    public List<Prestacion> listaDeSolicitudesDePrestacionesDeUnProfesionalPendientes(String dni) {
        return prestacionRepositorio.findByEstadoActivaIsFalseAndPendienteDeRespuestaIsTrueAndPrestacionAceptadaIsFalseAndProfesionalUsuarioDni(dni);
    }
    @Transactional
    @Override
    public void confirmarPrestacion(Long idPrestacion, Boolean confirmacion) throws Exception {
        Prestacion prestacionEncontrada =actualizarPrestacionConfirmada(idPrestacion,confirmacion);
        cancelarPrestacionesPendientes(idPrestacion);
        calificacionServicio.crearCalificacion(prestacionEncontrada);
    }

    @Override
    public void crearPrestacion(Prestacion prestacion ) {
        prestacionRepositorio.save(prestacion);
    }

    @Override
    public Prestacion obtenerUltimaPrestacionSolicitadaPorElUsuario(String dni) {
        return prestacionRepositorio.findFirstByUsuarioDniOrderByIdDesc(dni);
    }

    @Override
    public Prestacion obtenerPrestacionActivaPorDniDelUsuario(String dni) {
        return prestacionRepositorio.findFirstByEstadoActivaIsTrueAndPendienteDeRespuestaIsFalseAndPrestacionAceptadaIsTrueAndUsuarioDni(dni);
    }

    @Override
    public List<Prestacion> obtenerListaDePrestacionesPorIdProfesional(Long id) {
        return prestacionRepositorio.findByProfesionalId(id);
    }

    @Override
    public List<Prestacion> obtenerListaDePrestacionesPorIdUsuario(Long id) {
        return prestacionRepositorio.findByUsuarioId(id);
    }

    @Override
    public Prestacion obtenerPrestacionPorId(Long id) throws Exception {
        if(prestacionRepositorio.findPrestacionById(id)!=null)
            return prestacionRepositorio.findPrestacionById(id);
        throw new Exception("no existe prestacion con este Id"+id);
    }

    @Override
    public void finalizarPrestacionActiva(Long id) {
        Prestacion prestacionEncontrada= prestacionRepositorio.findPrestacionById(id);
        prestacionEncontrada.setEstadoActiva(false);
        prestacionEncontrada.getProfesional().setDisponible(true);
        prestacionEncontrada.setFechaHoraFinalizacion(obtenerFechaActual());

        prestacionRepositorio.save(prestacionEncontrada);
    }

    @Override
    public Prestacion obtenerPrestacionActivaPorDniDelProfesional(String dniProfesional) {
        return prestacionRepositorio.findFirstByEstadoActivaIsTrueAndPendienteDeRespuestaIsFalseAndPrestacionAceptadaIsTrueAndProfesionalUsuarioDni(dniProfesional);
    }

    @Override
    public void pagarPrestacion(Long id) {
        Optional<Prestacion> prestacionEncontrada =prestacionRepositorio.findById(id);
        prestacionEncontrada.get().setPagada(true);
        prestacionRepositorio.save(prestacionEncontrada.get());
    }

    @Override
    public void cancelarUnaPrestacionPendienteDeUnProfesional(Long idPrestacion) {
      Prestacion prestacion = prestacionRepositorio.findPrestacionById(idPrestacion);
      prestacion.setPendienteDeRespuesta(false);
      prestacionRepositorio.save(prestacion);
    }

    @Override
    public List<Prestacion> obtenerPrestacionesFinalizadasPorUnProfesional(Long idProfesional) throws Exception {
        Profesional profesional = profesionalServicio.obtenerProfesionalPorId(idProfesional);
        System.out.println(profesional.getId());
        System.out.println();
        if(profesional!= null){
            return prestacionRepositorio.obtenerPrestacionesFinalizadasPorUnProfesional(idProfesional);
         }
        throw new Exception("No existe profesional con id: "+ idProfesional);
    }

    @Override
    public List<Prestacion> obtenerPrestacionesFinalizadasPorUnUsuario(Long idUsuario) throws Exception {
        Usuario usuario = usuarioServicio.obtenerUsuarioPorId(idUsuario);
        if(usuario!= null){
            return prestacionRepositorio.obtenerPrestacionesFinalizadasPorUnUsuario(idUsuario);
        }
        throw new Exception("No existe usuario con id: "+ idUsuario);
    }

    @Override
    public Integer cantidadDeVecesRealizoUnaHabilidad( Long idProfesional,Long idHabilidad) {
        return prestacionRepositorio.cantidadDeVecesRealizoUnaHabilidad(idProfesional, idHabilidad).size();
    }

    private void cancelarPrestacionesPendientes(Long idPrestacion) throws Exception {
        Prestacion prestacionEncontrada = prestacionRepositorio.findPrestacionById(idPrestacion);
        cancelarPrestacionesPendientesDeUnProfesional(prestacionEncontrada.getProfesional().getId());
    }

    private void cancelarPrestacionesPendientesDeUnProfesional(Long idProfesional) throws Exception {
        try{
        List<Prestacion> listaPrestaciones = prestacionRepositorio.findPrestacionByEstadoActivaIsFalseAndPendienteDeRespuestaIsTrueAndPrestacionAceptadaIsFalseAndProfesionalId(idProfesional);
            for (Prestacion prestacion:listaPrestaciones) {
                prestacion.setPendienteDeRespuesta(false);
                prestacionRepositorio.save(prestacion);
            }
        }catch (Exception e){
            throw new Exception("Error en seteo de la prestacion: " + e.getMessage());
        }
    }

    private Prestacion actualizarPrestacionConfirmada(Long idPrestacion, Boolean confirmacion) throws Exception {
        Prestacion prestacionEncontrada =validarPrestacion(idPrestacion);
        try{
            prestacionEncontrada.setEstadoActiva(true);
            prestacionEncontrada.setPendienteDeRespuesta(false);
            prestacionEncontrada.setPrestacionAceptada(confirmacion);
            prestacionEncontrada.getProfesional().setDisponible(false);
            prestacionEncontrada.setFechaHoraInicio(obtenerFechaActual());
            prestacionRepositorio.save(prestacionEncontrada);
            return prestacionEncontrada;
        }catch (Exception e){
            throw new Exception("Error en seteo de la prestacion: " + e.getMessage());
        }
    }

    private Prestacion validarPrestacion(Long idPrestacion) throws Exception {
        Prestacion prestacionValidada = prestacionRepositorio.findPrestacionById(idPrestacion);
        if(prestacionValidada != null){
            return prestacionValidada;
        }else
            throw new Exception("Error al validar la prestacion. No existe prestacion");
    }
}
