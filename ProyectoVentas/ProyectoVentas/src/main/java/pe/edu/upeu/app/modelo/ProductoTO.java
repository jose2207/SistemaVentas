/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.upeu.app.modelo;

import lombok.Data;



/**
 *
 * @author ACER ASPIRE
 */

@Data
public class ProductoTO {
    public String nombre;
    public Double pu, utilidad, stock;
    public int id_producto, id_categoria, id_marca;

    
    
}
