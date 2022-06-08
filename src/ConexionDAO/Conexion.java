/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConexionDAO;

import DTO.Datos;
import com.sun.jdi.connect.spi.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Usuario
 */
public class Conexion {
    
    java.sql.Connection conexion;
     List<Datos> listaDatos = new ArrayList<Datos>();
     
     private void Abrir(){
       String user="root";
        String password="root";
        String url="jdbc:mysql://localhost:3306/proyecto?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException ex){
    ex.printStackTrace();
        }
    }
     public void cerrar(){
         try{
             conexion.close();
         }catch(SQLException ex){
             Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE,null,ex);
         }
     }
     public boolean insertar(Datos datos) {
       boolean estado = true;
       
       try{
           Abrir();
           PreparedStatement ps = conexion.prepareStatement("insert into datos(nombre, edad, sexo, correo, celular) values (?,?,?,?,?)");
           ps.setString(1, datos.getNombre());
            ps.setString(2, datos.getEdad());
            ps.setString(3, datos.getSexo());
            ps.setString(4, datos.getCorreo());
            ps.setString(5, datos.getCelular());
            ps.execute();
           
       }catch(SQLException ex){
           ex.printStackTrace();
           estado =false;
       }finally{
           cerrar();
       }
       return estado;
   }
      public boolean actualizar(Datos datos){
         boolean estado = true;
         try{
             Abrir();
              PreparedStatement ps = conexion.prepareCall("update Datos set nombre = ?, edad= ?, sexo = ?, correo = ?, celular = ? where id = ?");
            ps.setString(1, datos.getNombre());
            ps.setString(2, datos.getEdad());
            ps.setString(3, datos.getSexo());
            ps.setString(4, datos.getCorreo());
            ps.setString(5, datos.getCelular());
          
            ps.executeUpdate();
            
         }catch(SQLException ex){
            ex.printStackTrace();
            estado = false;
        }finally{
            cerrar();
        }        
        return estado;
    }
      public boolean consultartodos(){
   boolean estado = true;
   try{
    
        Abrir();
        PreparedStatement ps = conexion.prepareStatement("select * from Datos");
        ResultSet rs = ps.executeQuery();
        Datos datos;
        
        while(rs.next()){
            datos = new Datos(rs.getNString("nombre"),rs.getString("edad"),rs.getString("sexo"),rs.getString("correo"),rs.getString("celular"));
            //datos.setNombre(rs.getNString("nombre"));
            listaDatos.add(datos);
        }
    
}catch(SQLException ex){
            ex.printStackTrace();
            estado = false;
        }finally{
            cerrar();
        }        
        return estado;
        
   }
   
    

   
      public List<Datos> getListaDatos(){
        return listaDatos;
      }
     
    public boolean borrar(int id){
       boolean estado =true;
         try{
    
        Abrir();
        PreparedStatement ps = conexion.prepareStatement("delete from Datos where id = ?");
        ps.setInt(1, id);
        ps.execute();      
      
}catch(SQLException ex){
            ex.printStackTrace();
            estado = false;
        }finally{
            cerrar();
        }        
        return estado;
        
   }
    
}
