package com.learningjava.rest.spring.front.controller;

import com.learningjava.rest.spring.core.ReadDB;
import com.learningjava.rest.spring.core.Restaurant;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * com.learningjava.rest.spring.front
 * Class
 * By berto. 12/02/2018
 */

// anotacion que controla RestAPI y parsea su informacion

@RestController
public class ApiRestController {

    //anotacion que indica la ruta  donde se va a pasar la informacion, se le puede definir los verbos, en este caso GET
    @RequestMapping(path = "/restaurants",method = RequestMethod.GET)
    //metodo que nos permite sacar una lista de restaurantes
    public List<Restaurant> list() {
        //se crea una clase no estatica para la incorporacion de sus metodos
        ReadDB dbQuery = new ReadDB();
       //se crea la lista de los objetos a devolver usando un metodo del objeto anteriormente creado
        List<Restaurant> restaurants = dbQuery.readRestaurantAPI();
        // se devuelven los restaurantes
        return restaurants;
    }

}
