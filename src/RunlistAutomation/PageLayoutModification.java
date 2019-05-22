package RunlistAutomation;


import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;


public class PageLayoutModification {

  /* public static void main(String argv[]) {

      try {
         
        // addFieldOnPageLayout();
         addRelatedListOnPageLayout();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }*/
   
   public static void addFieldOnPageLayout(String pageLayout, String section, String addRemoveField, String priorField) throws Exception{
	   
	   section="Account Information";
	   pageLayout=pageLayout.replace("(", "%28");
	   pageLayout=pageLayout.replace(")", "%29");
	   @SuppressWarnings("resource")
	   ZipEntry zipEntry = new ZipFile("components.zip").getEntry("unpackaged/layouts/Account-Account %28Marketing%29 Layout.layout");
	   /*Enumeration<? extends ZipEntry> entries = zipFile.entries();

	   while(entries.hasMoreElements()){
	       ZipEntry entry = entries.nextElement();
	       if(entry.isDirectory()){
	           System.out.println("dir  : " + entry.getName());
	       } else {
	           System.out.println("file : " + entry.getName());
	       }
	   }*/
	   @SuppressWarnings("resource")
	   InputStream inputStream = new ZipFile("components.zip").getInputStream(zipEntry);
	   //File fXmlFile = new File("/components/unpackaged/layouts/Account-Account %28Marketing%29 Layout.layout-meta.xml"); //Your File Name
       
	   DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
       DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
       Document doc = dBuilder.parse(inputStream);
       doc.getDocumentElement().normalize();
       
       
       Element newElement = doc.createElement("layoutItems");
     
       
       Element behaviourElement = doc.createElement("behavior");
       behaviourElement.appendChild(doc.createTextNode("Edit"));
       newElement.appendChild(behaviourElement);

       Element fieldElement = doc.createElement("field");
       fieldElement.appendChild(doc.createTextNode(addRemoveField)); //Your Field Name
       newElement.appendChild(fieldElement);
       
       
       
       // root element
       NodeList nList = doc.getElementsByTagName("layoutSections");
       Node nNode = nList.item(0);
       Element eElement = (Element) nNode;
       
       Element parentElement = (Element) eElement.getElementsByTagName("layoutColumns").item(0);
       
      
       
       
       NodeList nListChild  = (NodeList) eElement.getElementsByTagName("layoutItems");
       
       //System.out.println("RefChilds are:"+nListChild);
       //System.out.println("Child List is:"+nList);
       
       for (int temp = 0; temp < nListChild.getLength(); temp++) {
      	 
	       //Loop over the childs and add the field accordingly
	       //reference of child before which this element needs to go, this will be via for loop
	       //Find the child element refChild.getElementsByTagName("field").item(0).getTextContent()
	       Node nNodeChild = nListChild.item(temp);
	       Element eElementChild = (Element) nNodeChild;
          // System.out.println("Ref child element name is:"+eElementChild.getElementsByTagName("field").item(0).getTextContent());
      	 
	       if(priorField.equals(eElementChild.getElementsByTagName("field").item(0).getTextContent())) { //Before Field name
	    	   
	    	   parentElement.insertBefore(newElement, eElementChild);
	       }
       }

       
       
       TransformerFactory transformerFactory = TransformerFactory.newInstance();
       Transformer transformer = transformerFactory.newTransformer();
       
       DOMSource source = new DOMSource(doc);
       StreamResult result = new StreamResult(new File("Test.xml"));
       transformer.setOutputProperty(OutputKeys.INDENT, "yes");
       transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC,"yes");
       transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
       StringWriter output = new StringWriter();
       transformer.transform(source, new StreamResult(output));
           
       //StringWriter output = new StringWriter();
       String gbStr = output.toString();
      // System.out.println("gbStr = " + gbStr);
       
       /*ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        
       byte buf[] = gbStr.getBytes(); 
        
       ByteArrayInputStream in = new ByteArrayInputStream(buf); 
        
       BufferedInputStream bis = new BufferedInputStream(in); 
        
       ZipOutputStream zipOS = new ZipOutputStream(baos);
        
       byte bytes[] = new byte[4096];
        
       zipOS.putNextEntry(new ZipEntry("unpackaged/layouts/Account-Account %28Marketing%29 Layout.layout"));
        
       int bytesRead;
        
       while ((bytesRead = bis.read(bytes)) != -1) {
        
           zipOS.write(bytes, 0, bytesRead);
       }
       zipOS.closeEntry();*/
       
       
       
       //ZipFile zipFile = new ZipFile("test.zip");
       final ZipOutputStream zos = new ZipOutputStream(new FileOutputStream("componentsOutput.zip"));
       for(Enumeration e = new ZipFile("components.zip").entries(); e.hasMoreElements(); ) {
           ZipEntry entryIn = (ZipEntry) e.nextElement();
           if (entryIn.getName().equalsIgnoreCase("unpackaged/layouts/Account-Account %28Marketing%29 Layout.layout")) {    
        	   //zos.putNextEntry(entryIn);
        	   zos.putNextEntry(new ZipEntry("unpackaged/layouts/Account-Account %28Marketing%29 Layout.layout"));

               //InputStream is = zipFile.getInputStream(entryIn);
               
               
               byte buf[] = gbStr.getBytes(); 
                
               ByteArrayInputStream in = new ByteArrayInputStream(buf); 
                
               BufferedInputStream bis = new BufferedInputStream(in); 
                
                
               byte bytes[] = new byte[4096];
                
               //zipOS.putNextEntry(new ZipEntry("unpackaged/layouts/Account-Account %28Marketing%29 Layout.layout"));
                
               int bytesRead;
                
               while ((bytesRead = bis.read(bytes)) != -1) {
                
            	   zos.write(bytes, 0, bytesRead);
               }
              // zipOS.closeEntry();
           }
           else if (entryIn.getName().equalsIgnoreCase("unpackaged/package.xml")) {
        	   zos.putNextEntry(new ZipEntry("unpackaged/package.xml"));
        	   
        	   InputStream is = new ZipFile("components.zip").getInputStream(entryIn);
               
               byte buf[] =  IOUtils.toByteArray(is);
               ByteArrayInputStream in = new ByteArrayInputStream(buf); 
               
               BufferedInputStream bis = new BufferedInputStream(in);
               //BufferedInputStream bis = new BufferedInputStream(in); 
               
               
               byte bytes[] = new byte[4096];
                
               int bytesRead;
                
               while ((bytesRead = bis.read(bytes)) != -1) {
                
            	   zos.write(bytes, 0, bytesRead);
               }
           }
           
           zos.closeEntry();
       }
       zos.close();
       
       // Output to console for testing
      // StreamResult consoleResult = new StreamResult(System.out);
      // transformer.transform(source, consoleResult);
       
       
     
   }
   
   public static void addRelatedListOnPageLayout() throws Exception{
   
   }
}