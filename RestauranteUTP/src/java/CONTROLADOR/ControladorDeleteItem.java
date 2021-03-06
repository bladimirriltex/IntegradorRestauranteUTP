/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package CONTROLADOR;

import DAO.PlatoDAO;
import DTO.Articulo;
import DTO.Plato;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Edú
 */
@WebServlet(name = "ControladorDeleteItem", urlPatterns = {"/borraritem"})
public class ControladorDeleteItem extends HttpServlet {
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        int idplato=Integer.parseInt(request.getParameter("idplato"));
        
        HttpSession sessioncarrito=request.getSession(true);
        ArrayList<Articulo> articulos= sessioncarrito.getAttribute("carrito")==null ?  null : (ArrayList)sessioncarrito.getAttribute("carrito");
        
        if (articulos!=null) {
            for (Articulo articulo : articulos) {
                if (articulo.getId_plato()==idplato) {
                    articulos.remove(articulo);
                    break;
                }
            }
        }
        
        
        double total=0;
        PlatoDAO plt=new PlatoDAO();
        
        for (Articulo a : articulos) {
            
            Plato plato=plt.list(a.getId_plato());
            total=total+a.getCantidad()*plato.getPrecio_plato();
        }
        
        response.getWriter().print(Math.round(total*100.0)/100.0);
        
        
        
        
        
        
        
        
        
        
        
    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
}
