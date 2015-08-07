package worksBeyond.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import worksBeyond.model.*;

import org.apache.commons.io.IOUtils;

//import org.hibernate.Hibernate;
import worksBeyond.neo4jRepository.DocumentRepository;
import worksBeyond.Service.DocumentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Transactional
public class DocumentController {
	
	@Autowired
	private DocumentService documentService;
	

	
@RequestMapping(value= "/uploadDocument")
public String index(Map<String, Object> map){
	
	try{
		map.put("document", new Document());
		map.put("documentList", documentService.getAllDocuments());
	}catch(Exception e){
		System.out.println("DOCUMENT SERVICE LIST VALUE: " + documentService.getAllDocuments());
		e.printStackTrace();
	}
	return "uploadDocument";
}
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(
			@ModelAttribute(value="document") Document document,
			@RequestParam("file") MultipartFile file){
				
				System.out.println("Name: " + document.getName());
				System.out.println("Desc:" + document.getDescription());
				System.out.println("File: " + file.getName());
				System.out.println("Content Type: " + file.getContentType());
				
				try{
					InputStream documentInput = file.getInputStream();
					byte[] documentContent = IOUtils.toByteArray(documentInput);
					document.setContent(documentContent);
					document.setFilename(file.getOriginalFilename());
					document.setContentType(file.getContentType());
					document.setCreated(null);
				} catch(IOException e){
					e.printStackTrace();
				}
				
				try{
					documentService.save(document);
				} catch(Exception e){
					e.printStackTrace();
				}
				
				return "redirect:/uploadDocument" ;
			}
	
	@RequestMapping(value = "/download/{documentId}")
	public String download(@PathVariable("documentId") Long documentId,
			HttpServletResponse response){
		
		Document document = documentService.get(documentId);
		System.out.println("Document to download : " + document.getName());
		try{
			response.setHeader("Content-Disposition", "inline;filename=\""+ document.getFilename()+"\"");
			OutputStream out = response.getOutputStream();
			response.setContentType(document.getContentType());
			InputStream documentInputStream = new ByteArrayInputStream(document.getContent());
			IOUtils.copy(documentInputStream, out);
			out.flush();
			out.close();
		}catch (IOException e){
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/remove/{documentId}")
	public String remove(@PathVariable("documentId") Long documentId){
		
		documentService.delete(documentId);
		
		return "redirect:/uploadDocument.html";
	}
	
	@RequestMapping(value= "/publication/{documentId}")
    public ModelAndView publications(@PathVariable("documentId") Long documentId){
    	ModelAndView mav = new ModelAndView();
    	Document publication = documentService.get(documentId);
    	mav.addObject("publication", publication);
    	mav.setViewName("publication");
    	
        return mav;
    } 
	
	@RequestMapping(value="/search", method = RequestMethod.GET)
	public ModelAndView showSearch(
			@ModelAttribute("search")Document document){
		return new ModelAndView("search");
	}
	
	
	@RequestMapping(value="/search", method = RequestMethod.POST)
	public ModelAndView searchPublications(@ModelAttribute("search")Document document, @RequestParam("name")String publicationName){
		//add logic to populate search results 
	
	List<Document> pubList = documentService.searchCriteria(publicationName);
	
	ModelAndView mav = new ModelAndView();
	mav.addObject("publicationList", pubList);
	mav.addObject("documents",  document);
	mav.setViewName("search");

	return mav;
		
	}
	
	
	


}
