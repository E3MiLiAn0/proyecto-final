package com.cuatrodecopas.salud.core.servicios.implementaciones;

import com.cuatrodecopas.salud.core.entidades.Profesional;
import com.cuatrodecopas.salud.core.entidades.ProfesionalHabilidad;
import com.cuatrodecopas.salud.core.servicios.interfaces.ProfesionalServicio;
import com.cuatrodecopas.salud.data.gateways.interfaces.ValidacionRegistroProfesionalGateway;
import com.cuatrodecopas.salud.data.repositorios.DireccionRepositorio;
import com.cuatrodecopas.salud.data.repositorios.ProfesionalHabilidadRespositorio;
import com.cuatrodecopas.salud.data.repositorios.ProfesionalRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfesionalServicioImpl implements ProfesionalServicio {

    @Autowired
     ProfesionalRepositorio profesionalRepositorio;

    @Autowired
    DireccionRepositorio direccionRepositorio;

    @Autowired
    ValidacionRegistroProfesionalGateway validacionRegistroProfesionalGateway;
    @Autowired
    ProfesionalHabilidadRespositorio profesionalHabilidadRespositorio;

    @Override
    public List<Profesional> obtenerListProfesionalesConGuardiaActivaYDisponible() {
        return profesionalRepositorio.findByGuardiaActivaIsTrueAndDisponibleIsTrue();
    }

    @Override
    public Profesional obtenerProfesionalPorDni(String dni) {
        return profesionalRepositorio.profesionalPorDniUsuario(dni);
    }

    @Override
    public List<Profesional> obtenerListProfesionalesConGuardiaActivaYDisponiblePorLocalidad(String localidad) {
        return profesionalRepositorio.findByGuardiaActivaIsTrueAndAndDisponibleIsTrueAndDireccionProfesional_Localidad(localidad);
    }

    @Override
    public void registrarProfesional(Profesional profesional) throws Exception {

        if (profesionalExiste(profesional)== null){
            profesional.getUsuario().setEsProfesional(true);
            direccionRepositorio.save(profesional.getDireccionProfesional());
            profesionalRepositorio.save(profesional);
        }
        else {
            throw new Exception("ya existe este profesional");
        }
    }

    @Override
    public Profesional obtenerProfesionalPorId(Long idProfesional) {
        return profesionalRepositorio.findProfesionalById(idProfesional);
    }

    @Override
    public List<ProfesionalHabilidad> obtenerTodasLasHabilidadesDeUnProfesionalPorId(Long idProfesional) throws Exception {
        Profesional profesional = obtenerProfesionalPorId(idProfesional);
        validarProfesional(profesional);
        return profesionalHabilidadRespositorio.listaDeHabilidadesDeUnProfesional(idProfesional);
    }

    @Override
    public List<Profesional> filtrarProfesionalesPorLocalidadYHabilidad(String localidad, Long idHabilidad) {
            return profesionalHabilidadRespositorio.ProfesionalesPorLocalidadYHabilidad(idHabilidad,localidad);
    }
    @Override
    public void actualizarGuardia(Profesional profesional) throws Exception {
        actualizarProfesionalParaLaGuardia(profesional);
    }

    private void actualizarProfesionalParaLaGuardia(Profesional profesional) throws Exception {
        Profesional profesionalEncontrado =validarProfesional(profesional);

        try{
            profesionalEncontrado.setGuardiaActiva(profesional.getGuardiaActiva());
            profesionalEncontrado.setEspecialidadDeLaMatricula("paso por aca pa");
            profesionalEncontrado.getDireccionProfesional().setLatitud(profesional.getDireccionProfesional().getLatitud());
            profesionalEncontrado.getDireccionProfesional().setLongitud(profesional.getDireccionProfesional().getLongitud());
            profesionalRepositorio.save(profesionalEncontrado);
        }catch (Exception e){
            throw new Exception("Error en seteo de la guardia: " + e.getMessage());
        }
    }
    public Profesional validarProfesional(Profesional profesional) throws Exception {
           Profesional profesionalValidado = profesionalRepositorio.findByUsuarioDniAndEstadoDeLaMatriculaIsTrue(profesional.getUsuario().getDni());
           if(profesionalValidado != null){
               return profesionalValidado;
           }else
               throw new Exception("Error al validar matricula o DNI. No existe profesional");
    }

    public Profesional profesionalExiste(Profesional profesional){
        return profesionalRepositorio.findByUsuarioDni(profesional.getUsuario().getDni());
    }

    @Override
    public String validarRegistroProfesional(String matricula,String dniProfesional) {
        return validacionRegistroProfesionalGateway.buscarProfesionalPorMatricula(matricula,dniProfesional);
    }


}
