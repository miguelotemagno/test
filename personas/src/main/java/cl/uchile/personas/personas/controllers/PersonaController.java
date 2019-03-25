package cl.uchile.personas.personas.controllers;

import cl.uchile.personas.personas.entities.dto.Persona;
import cl.uchile.personas.personas.entities.dto.Ping;
import cl.uchile.personas.personas.entities.dto.Response;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
public class PersonaController {
    private Map<String,Persona> personas;

    public PersonaController() {
        this.personas = new HashMap<String,Persona>();
    }

    @RequestMapping("/ping")
    public Ping ping() {
        Ping pong = new Ping();
        pong.pong = "PONG";
        return pong;
    }

    /**
     * Listar:
     * Despliega los registros existentes en el Map
     * */
    @RequestMapping("/personas")
    public ArrayList<Persona> Listar() {
        ArrayList<Persona> list = new ArrayList<Persona>();

        for (String key: this.personas.keySet()) {
            list.add(this.personas.get(key));
        }

        return list;
    }

    /**
     * Insert:
     * Ingresa valores del JSON en un nuevo registro dentro del Map, El ID lo debe generar el cliente
     * Datos input ejemplo:
     * {
     *   "nombre": "fulano de tal",
     *   "userName": "foo",
     *   "rut": "123456-7",
     *   "idPersona": "1",
     *   "emailPersonal": "foo@gmail.com",
     *   "emailAlternativo": "foo@gmail.com",
     *   "direccion": "mi casa"
     * }
     * */
    @RequestMapping(value = "/personas/insert", method = RequestMethod.POST)
    public Response Insert(@RequestBody Persona persona) {
        Response msg = new Response();
        Boolean ok = true;

        if(persona != null
                && persona.nombre != null && persona.nombre.matches("\\w+(\\s+\\w+)+")
                && persona.userName != null && persona.userName.matches("^\\w+$")
                && persona.rut != null && persona.rut.matches("^\\d{1,3}([.]?\\d{3})+-[0-9k]$")
                && persona.idPersona != null && persona.idPersona.matches("^\\d+$")
                && persona.emailPersonal != null && persona.emailPersonal.matches("^\\w+([.]?\\w+)*@\\w+([.]?\\w+)*[.]\\w{2,3}$")
                && persona.emailAlternativo != null && persona.emailAlternativo.matches("^\\w+([.]?\\w+)*@\\w+([.]?\\w+)*[.]\\w{2,3}$")
                && persona.direccion != null && persona.direccion.matches(".+"))
        {
            for (String key: this.personas.keySet()) {
                Persona register = this.personas.get(key);

                if( register.userName.equals(persona.userName)
                        || register.rut.replaceAll("[^0-9-]", "").equals(persona.rut.replaceAll("[^0-9-]", ""))
                        || register.idPersona.equals(persona.idPersona)
                        || register.nombre.equals(persona.nombre) && register.emailPersonal.equals(persona.emailPersonal)) {
                    msg.idPersona = "";
                    msg.message = "Persona a ingresar ya existe";
                    ok = false;
                }
            }
        }
        else
        {
            msg.idPersona = "";
            msg.message = "Faltan datos de la persona, o no estan bien ingresados";
            ok = false;
        }

        if (ok) {
            this.personas.put(persona.idPersona, persona);
            msg.idPersona = persona.idPersona;
            msg.message = "Datos ingresados satisfactoriamente";
        }

        return msg;
    }

    /**
     * Update:
     * Puede actualizar los datos de un registro en el Map.
     * Se actualizan solo aquellos campos presentes o distinto de null en la estructura JSON y que cumplan validacion
     * Datos input ejemplo:
     * http://localhost:8090/personas/update/2
     *
     * {
     *   "nombre": "fulano de tal",
     *   "emailPersonal": "foo@gmail.com",
     *   "direccion": "mi casa"
     * }
     * */
    @RequestMapping(value = "/personas/update/{id}", method = RequestMethod.POST)
    public Response Update(@PathVariable String id, @RequestBody Persona persona) {
        Response msg = new Response();
        Boolean ok = true;
        Persona register = this.personas.get(id);

        if(register == null) {
            msg.idPersona = "";
            msg.message = "No se ha encontrado registro";
            ok = false;
        }

        if (register != null && persona.nombre != null)
            if (persona.nombre.matches("\\w+(\\s+\\w+)+")) {
                register.nombre = persona.nombre;
            }
            else {
                msg.idPersona = "";
                msg.message = "Nombre de la persona no esta bien ingresado";
                ok = false;
            }
        if (register != null && persona.userName != null)
            if (persona.userName.matches("^\\w+$")) {
                register.userName = persona.userName;
            }
            else {
                msg.idPersona = "";
                msg.message = "UserName de la persona no esta bien ingresado";
                ok = false;
            }
        if (register != null && persona.rut != null)
            if (persona.rut.matches("^\\d{1,3}([.]?\\d{3})+-[0-9k]$")) {
                register.rut = persona.rut;
            }
            else {
                msg.idPersona = "";
                msg.message = "Rut de la persona no esta bien ingresado";
                ok = false;
            }
        if (register != null && persona.idPersona != null)
            if (persona.idPersona.matches("^\\d+$")){
                Persona existe = personas.get(persona.idPersona);
                if (existe != null && !existe.idPersona.equals(register.idPersona)) {
                    msg.idPersona = "";
                    msg.message = "Esta intentando sobreescribir datos de otra persona";
                    ok = false;
                }
                else
                    register.idPersona = persona.idPersona;
            }
            else {
                msg.idPersona = "";
                msg.message = "idPersona de la persona no esta bien ingresado";
                ok = false;
            }
        if (register != null && persona.emailPersonal != null)
            if (persona.emailPersonal.matches("^\\w+([.]?\\w+)*@\\w+([.]?\\w+)*[.]\\w{2,3}$")){
                register.emailPersonal = persona.emailPersonal;
            }
            else {
                msg.idPersona = "";
                msg.message = "EmailPersonal de la persona no esta bien ingresado";
                ok = false;
            }
        if (register != null && persona.emailAlternativo != null)
            if (persona.emailAlternativo.matches("^\\w+([.]?\\w+)*@\\w+([.]?\\w+)*[.]\\w{2,3}$")){
                register.emailAlternativo = persona.emailAlternativo;
            }
            else {
                msg.idPersona = "";
                msg.message = "emailAlternativo de la persona no esta bien ingresado";
                ok = false;
            }
        if (register != null && persona.direccion != null)
            if (persona.direccion.matches(".+")) {
                register.direccion = persona.direccion;
            }
            else {
                msg.idPersona = "";
                msg.message = "Direccion de la persona no esta bien ingresado";
                ok = false;
            }

        if (ok) {
            if (register.idPersona == null)
                register.idPersona = id;

            this.personas.put(register.idPersona, register);
            msg.idPersona = register.idPersona;
            msg.message = "Datos actualizados satisfactoriamente";
        }

        return msg;
    }

    /**
     * Delete:
     * Elimina un registro del Map
     * Ejemplo
     * http://localhost:8090/personas/delete/2
     * */
    @RequestMapping(value = "/personas/delete/{id}")
    public Response Delete(@PathVariable String id) {
        Response msg = new Response();
        Boolean ok = true;
        Persona register = this.personas.get(id);

        if(register == null) {
            msg.idPersona = "";
            msg.message = "No se ha encontrado registro";
            ok = false;
        }

        if (ok) {
            this.personas.remove(register.idPersona);
            msg.idPersona = register.idPersona;
            msg.message = "Datos eliminados satisfactoriamente";
        }

        return msg;
    }
}
