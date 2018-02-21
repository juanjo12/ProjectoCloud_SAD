package com.learningjava.rest.spring.core;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

//clase que destinada al backend, con la que se accede a los datos
public class ReadDB {
//conexion a la base de datos
    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String THIN_URL = "jdbc:oracle:thin:@35.205.41.45:1521:XE";
    private static final String USER = "usuari";
    private static final String PASSWORD = "usuari";
// metodo para ejecutar las consultas con functional programming
    private Object executeQuery(String query, Function<ResultSet, Object> f) {
        Connection con;
        Statement stmt;
        try {
            Class.forName(DRIVER);
            con = DriverManager.getConnection(THIN_URL, USER, PASSWORD);
            stmt = con.createStatement();
            // aqui se ejecuta la query mediante un statement
            ResultSet rs = stmt.executeQuery(query);
            // se crea un objeto generico y se le aplica la funcion f al resultset
            Object o = f.apply(rs);
            // se cierra el statement y la conexion
            stmt.close();
            con.close();
            // devuelve el objeto generico
            return o;
        } catch (Exception e) {
            return null;
        }
    }

   /* private Object executePreparedStatement(String query
            , Function<PreparedStatement, PreparedStatement> psFunction
            , Function<ResultSet, Object> f) {
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(DRIVER);
            con = DriverManager.getConnection(THIN_URL, USER, PASSWORD);
            ps = con.prepareStatement(query);
            ps = psFunction.apply(ps);
            ResultSet rs = ps.executeQuery(query);
            Object o = f.apply(rs);
            ps.close();
            con.close();
            return o;
        } catch (Exception e) {
            return null;
        }
    }*/

// se crea un metodo List, de manera que podemos utilizar arraylist, vectores... de manera generica
    public List readRestaurantAPI() {
        //se crea un ArrayList para introducir los datos,
        List<Restaurant> arrayRestaurants = new ArrayList<>();
        // se crea un objeto que mapea la informacion de la base de datos, esto se encuentra en una clase generica para cualquier objeto
        ResultSetMapper<Restaurant> mapper = new ResultSetMapper<>();
        try {
        //consulta la base de datos, que se introduce en un String
            final String query = "SELECT R.RES_CODI,R.RES_NOM,R.RES_ADRECA,R.RES_WEB,R.RES_TELEFON,R.RES_URL_IMG,R.RES_MITJANA, TR.TRS_DESCRIPCIO FROM " +
                    "RESTAURANTS R,TRESTAURANTS TR WHERE  R.RES_TRS_CODI = TR.TRS_CODI";
                    //se crea un objeto apartir de la funcion para poder ejectuarla
                Function<ResultSet, Object> func = new Function<ResultSet, Object>() {
                    // se ejecuta un objeto con el metodo apply que nos ejecuta la funcion, y nos devuelve el mapeo mediante el resultset y la clase que queremos
                    public Object apply(ResultSet rs) {
                        return mapper.mapResultSetToObject(rs, Restaurant.class);
                    }
                };
                //indicamos al array creado anteriorme que ejecut el metodo functional, junto con la query
                arrayRestaurants = (ArrayList) executeQuery(query, func);

        } catch (Exception e) {
            System.out.println(e.toString());
        }
        //devuelve la informacion introducida en el ArrayList
        return arrayRestaurants;
    }

   /* public List<Restaurant> getRestaurantWithPS(String searchName) {
        List<Restaurant> arrayRestaurants = new ArrayList<>();
        try {
            executePreparedStatement("SELECT * FROM (SELECT R.RES_NOM, R.RES_ADRECA, R.RES_WEB, R.RES_TELEFON, R.RES_URL_IMG, R.RES_CODI, T.TRS_DESCRIPCIO FROM RESTAURANTS R, TRESTAURANTS T WHERE RES_TRS_CODI = TRS_CODI AND LOWER(R.RES_NOM) LIKE ? ORDER BY RES_MITJANA ASC) WHERE ROWNUM <= 5"
                    , rs -> {
                        try {
                            rs.setString(1, "%" + searchName.toLowerCase() + "%");
                        } catch (SQLException e) {
                            System.out.println("mek");
                        }
                        return rs;
                    }
                    , rs -> {
                        ResultSetMapper<Restaurant> mapper = new ResultSetMapper<>();
                        return mapper.mapResultSetToObject(rs, Restaurant.class);
                    });
        } catch (Exception e) {
            System.out.println("error");
        }
        return arrayRestaurants;
    }
 /* public List<Restaurant> readRestaurant(String search) {
        List<Restaurant> arrayRestaurants = new ArrayList<>();
        try {
            String query;

                query = "SELECT R.RES_CODI,R.RES_NOM,R.RES_ADRECA,R.RES_WEB,R.RES_TELEFON,R.RES_URL_IMG,R.RES_MITJANA, TR.TRS_DESCRIPCIO " +
                        "FROM RESTAURANTS R,TRESTAURANTS TR WHERE R.RES_TRS_CODI = TR.TRS_CODI" +
                        " AND LOWER (RES_NOM) LIKE '%" + search.toLowerCase() + "%'";

            ResultSetMapper<Restaurant> mapper = new ResultSetMapper<>();
            Function<ResultSet, Object> func = new Function<ResultSet, Object>() {
                public Object apply(ResultSet rs) {
                    return mapper.mapResultSetToObject(rs, Restaurant.class);
                }
            };
            arrayRestaurants = (ArrayList) executeQuery(query, func);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return arrayRestaurants;
    }
    public List getAll(String query, Class classname){
        arrayRestaurants = new ArrayList<>();
        Function<ResultSet, Object> func = new Function<ResultSet, Object>() {
            public Object apply(ResultSet rs) {
                return mapper.mapResultSetToObject(rs, Restaurant.class);
            }
        };
        arrayRestaurants = (ArrayList) executeQuery(query, func);
    }*/
}






