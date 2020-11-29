/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package back;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.rekognition.model.AmazonRekognitionException;
import com.amazonaws.services.rekognition.model.DetectLabelsRequest;
import com.amazonaws.services.rekognition.model.DetectLabelsResult;
import com.amazonaws.services.rekognition.model.DetectTextRequest;
import com.amazonaws.services.rekognition.model.DetectTextResult;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.Label;
import com.amazonaws.services.rekognition.model.Parent;
import com.amazonaws.services.rekognition.model.TextDetection;
import com.amazonaws.util.IOUtils;
import com.google.gson.Gson;
import java.nio.file.Paths;
import java.util.Vector;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Part;

/**
 *
 * @author luiss
 */
@MultipartConfig
@WebServlet(name = "miser", urlPatterns = {"/miser"})
public class miser extends HttpServlet {
private Gson gson = new Gson();
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
        PrintWriter out = response.getWriter();
        out.println("<h1>hola</h1>");
        
        Part filePart = request.getPart("file"); // Retrieves <input type="file" name="file">
    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
    InputStream fileContent = filePart.getInputStream();


        ByteBuffer imageBytes;
        out.println("<h1>DOS</h1>");
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    String finalAnimal;
    float finalProbability=0;
    Label viejoLabel;
    String mijson2 = new String();
    Vector v = new Vector();
    Vector vecPar = new Vector();
    public String getFotoText(AmazonRekognition awsRekClient,ByteBuffer photo){
        
        String finalText = "";
        String txtPartido [];
        
        finalText = "\"date\":[";
        
         DetectTextRequest request4 = new DetectTextRequest()
              .withImage(new Image()
              .withBytes(photo));
    

      try {
         DetectTextResult result = awsRekClient.detectText(request4);
         List<TextDetection> textDetections = result.getTextDetections();

         //System.out.println("Detected lines and words for " + photo);
         for (TextDetection text: textDetections) {
      
               /*  System.out.println("Detected: " + text.getDetectedText());
                 System.out.println("Confidence: " + text.getConfidence().toString());
                 System.out.println("Id : " + text.getId());
                 System.out.println("Parent Id: " + text.getParentId());
                 System.out.println("Type: " + text.getType());
                 System.out.println();*/
                 /*finalText = "Detected: " + text.getDetectedText() + "Confidence: " + text.getConfidence().toString() +
                            "Id : " + text.getId() +  "Parent Id: " + text.getParentId() + "Type: " + text.getType();*/
                
                 txtPartido = text.getDetectedText().split(" ");
                 
                 for(int k = 0 ; k<txtPartido.length;k++){
                     if(txtPartido[k].contains("/")){
                         finalText+="{\"fecha\":"  + "\""+ txtPartido[k] +"\"},";
                     }else{
                         if(txtPartido[k].contains(":")){
                             finalText+="{\"hora\":"  + "\""+ txtPartido[k] +"\"}]";
                         }
                     }
                     
                    // finalText += "nuevo:" + txtPartido[k].toString();
                 }
                         
                 return finalText;
         }
      } catch(AmazonRekognitionException e) {
         e.printStackTrace();
      }
        
        return finalText;
    }
    
    public Label getAnimalInfo(Label l){
        
        float newProb = l.getConfidence();
        if(finalProbability<newProb){
            finalProbability = newProb;
            
            return l;
        }else{
            return viejoLabel;
        }
        
        
    }
    
    public String isAnimalName(String tag, List <Parent> par){
        Object a = (Object) par.get(0);
        
        
        
        if(par.size()>0){
            for(Parent p: par){
                     return "" + tag +"p="  + p;
                     /*if(tag.compareTo(p.getName())!= 0){
                         return "" + tag +"p=" + p.getName();
                     }*/
                 }
        }else{
        return "";
        }
        return "";
        
        //return "tag"+ tag + "par" + par;
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        v = new Vector();
        mijson2 = new  String();
        
         PrintWriter out = response.getWriter();
        
         Part filePart = request.getPart("file"); // Retrieves <input type="file" name="file">
    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
    InputStream fileContent = filePart.getInputStream();


        ByteBuffer imageBytes;
        
        imageBytes = ByteBuffer.wrap(IOUtils.toByteArray(fileContent));

        //BasicAWSCredentials creds = new BasicAWSCredentials("aws-access-key", "aws-secret-key");
        //AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.defaultClient().withRegion("");
        AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.defaultClient();
        DetectLabelsRequest requestt = new DetectLabelsRequest()
                .withImage(new Image()
                        .withBytes(imageBytes))
                .withMaxLabels(10)
                .withMinConfidence(77F);

        try {
  // BasicAWSCredentials creds = new BasicAWSCredentials("aws-access-key", "aws-secret-key");
            DetectLabelsResult result = rekognitionClient.detectLabels(requestt);
            List <Label> labels = result.getLabels();
            List <Parent> Parents = null;
            
           
                
            
            //System.out.println("Detected labels for ");
            boolean isAnimal = false;
            for (Label label: labels) {
                
                
                
                
                v.add(label.getName());
                

            }
             
	 
	        
	       // response.setContentType("application/json");
	        response.setCharacterEncoding("UTF-8");
	        //out.print(mijson);
	        //out.flush(); 
                
                mijson2 = null;
            if(v.contains(new String("Animal"))){
                //out.println("hay un animal" + v);
                for (Label label2: labels){
                   // if(label2.getInstances().size() > 0){
                        
                        if(label2.getConfidence() > 85){
                            if(label2.getName().toLowerCase().compareTo("mammal") != 0 &&
                               label2.getName().toLowerCase().compareTo("animal") != 0 &&
                               label2.getName().toLowerCase().compareTo("canine") !=0 &&
                                    label2.getName().toLowerCase().compareTo("wildlife") !=0
                            ){
                        String mijson = this.gson.toJson(label2);
                       
                         mijson2 = "[{\"responseInfo\":200}," + "{\"animal\":[" +mijson +"]}]" ; //+ "{responseInfo:200}]}"; 
                        
                        out.print(mijson2);
                        mijson2 = "";
                      
                        out.flush();
                        break;
                            }
                        }
                        else{
                        mijson2 = "[{\"responseInfo\":400}]"  ; //+ "{responseInfo:200}]}"; 
                        
                        out.print(mijson2);
                        mijson2 = "";
                        out.flush();
                        break;
                        }
                   // }/*else{
                        //no hay sufifciente confianza
                        /*mijson2 = "";
                        mijson2 = "[{\"responseInfo\":400}]";*/
                        /* out.print("[{\"responseInfo\":400}]");
                         mijson2 = "";
                         out.flush(); */
                    //}
                    
                }
            }
            else{
                       
               
                        mijson2 = "[{\"responseInfo\":404}]";
                         out.print(mijson2);
                         mijson2 = "";
                         out.flush(); 
            }
               
            

        } catch (AmazonRekognitionException e) {
            e.printStackTrace();
            out.println("error" + e);
        }
        
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
