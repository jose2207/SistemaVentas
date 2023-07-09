/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pe.edu.upeu.app.dao;

import java.util.List;
import pe.com.syscenterlife.autocomp.ModeloDataAutocomplet;
import pe.edu.upeu.app.modelo.ProductoTO;

/**
 *
 * @author ACER ASPIRE
 */
public interface ProductoDaoI {
    
    public int actualizarStock(double cant, double idp);

    public int create(ProductoTO d);

    public int update(ProductoTO d);

    public int delete(String id) throws Exception;

    public List<ProductoTO> listCmb(String filter);

    public List <ProductoTO>listarProductos(); // no se si necesitare eso EN UN COMBO BOX pero lo dejo porsiacaso by: YERALD XDD

    public ProductoTO buscarProducto(String nombre);

    public void reportarProducto();
    
    public List<ModeloDataAutocomplet> listAutoComplet(String filter);
}
