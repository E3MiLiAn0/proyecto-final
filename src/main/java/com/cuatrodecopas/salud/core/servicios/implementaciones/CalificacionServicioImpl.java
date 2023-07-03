package com.cuatrodecopas.salud.core.servicios.implementaciones;

import com.cuatrodecopas.salud.core.entidades.Calificacion;
import com.cuatrodecopas.salud.core.entidades.Prestacion;
import com.cuatrodecopas.salud.core.servicios.interfaces.CalificacionServicio;
import com.cuatrodecopas.salud.data.repositorios.CalificacionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalificacionServicioImpl  implements CalificacionServicio {
    @Autowired
    private CalificacionRepositorio calificacionRepositorio;
    @Override
    public Calificacion obtenercalificacionPorId(Long idCalificacion) {
        return calificacionRepositorio.findCalificaionById(idCalificacion);
    }

    @Override
    public Double obtenerCalificacionPromedioDeUnProfesional(Long idProfesional) {
        List<Double> listaDePuntaje = calificacionRepositorio.listaDeCalificacionesDeProfesional(idProfesional);
        Double promedioTotal= calcularPromedioTotalDeCalificacion(listaDePuntaje);
        return promedioTotal;
    }

    @Override
    public Double obtenerCalificacionPromedioDeUnUsuario(Long idUsuario) {
        List<Double> listaDePuntaje = calificacionRepositorio.listaDeCalificacionesDeUsuario(idUsuario);
        Double promedioTotal= calcularPromedioTotalDeCalificacion(listaDePuntaje);
        return promedioTotal;
    }

    @Override
    public void crearCalificacion(Prestacion prestacionEncontrada) {
        Calificacion calificacion = new Calificacion();
        calificacion.setPrestacion(prestacionEncontrada);
       calificacionRepositorio.save(calificacion);
    }

    @Override
    public void profesionalCalificaUsuario(Long idPrestacion, String comentario, Integer puntaje) throws Exception {
        Calificacion calificacionEncontrada=calificacionRepositorio.findByPrestacionId(idPrestacion);
        if (calificacionEncontrada == null){
            throw new Exception("no existe una calificacion con el id de prestacion: "+ idPrestacion );
        }
        calificacionEncontrada.setPuntajeDeProfesionalAUsuario(puntaje);
        calificacionEncontrada.setCometarioProfesionalAUsuario(comentario);
        calificacionRepositorio.save(calificacionEncontrada);
    }

    @Override
    public void usuariolCalificaProfesiona(Long idPrestacion, String comentario, Integer puntaje) throws Exception {
        Calificacion calificacionEncontrada=calificacionRepositorio.findByPrestacionId(idPrestacion);
        if (calificacionEncontrada == null){
            throw new Exception("no existe una calificacion con el id de prestacion: "+ idPrestacion );
        }
        calificacionEncontrada.setPuntajeDeUsuarioAProfesional(puntaje);
        calificacionEncontrada.setCometarioUsuarioAProfesional(comentario);
        calificacionRepositorio.save(calificacionEncontrada);
    }

    private Double calcularPromedioTotalDeCalificacion(List<Double> listaDePuntaje) {
        Double promedioTotal = listaDePuntaje.stream()
                .filter(calificacion -> calificacion > 0)
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);

        return promedioTotal;
    }
/*

    @Override
    public void usuarioCalificaProfesional(Calificacion calificacion) throws Exception {
        validarDatosDeLaCalificacion(calificacion);
        if (calificacion.getPuntajeDeUsuarioAProfesional()>=1 ||
                calificacion.getPuntajeDeUsuarioAProfesional()<=5){
            Calificacion calificacionEncontrada = calificacionRepositorio.findCalificaionById(calificacion.getId());
            calificacionEncontrada.setPuntajeDeUsuarioAProfesional(calificacion.getPuntajeDeUsuarioAProfesional());
            calificacionEncontrada.setCometarioUsuarioAProfesional(calificacion.getCometarioUsuarioAProfesional());
            calificacionRepositorio.save(calificacionEncontrada);
            Profesional profesionalEncontrado = profesionalRepositorio.findProfesionalById(calificacionEncontrada.getProfesional().getId());
            profesionalEncontrado.getListaDeCalificacionesRecibidas().add(calificacionEncontrada);
            profesionalRepositorio.save(profesionalEncontrado);


        }

    }

    private void validarDatosDeLaCalificacion(Calificacion calificacion) throws Exception {
        try {
            Profesional profesionalValidado= profesionalServicio.validarProfesional(calificacion.getProfesional());
            Usuario usuarioEncontrado=usuarioServicio.obtenerUsuarioPorDni(calificacion.getUsuario().getDni());
            prestacionServicio.obtenerPrestacionPorId(calificacion.getPrestacion().getId());

        }
        catch (Exception e){
            throw new Exception("Fallo la validadcion de datos de la calificacion por  "+e.getMessage());
        }

    }

    @Override
    public void usuarioCalificaPrestacion(Calificacion calificacion) {

    }

    @Override
    public void profesionalCalificaUsuario(Calificacion calificacion) {

    }

    @Override
    public void profesionalCalificaPrestacion(Calificacion calificacion) {

    }

    @Override
    public void guardarCalificacion(Calificacion calificacion) {
        calificacionRepositorio.save(calificacion);
    }*/
}
