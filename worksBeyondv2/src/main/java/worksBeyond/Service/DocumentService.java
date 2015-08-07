package worksBeyond.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import worksBeyond.model.Document;
import worksBeyond.neo4jRepository.DocumentRepository;


@Service
@Transactional
public class DocumentService {
	
	@Autowired DocumentRepository documentRepository;
	
	public void save(Document document){
		documentRepository.save(document);
	}
	
	public void delete(Long documentId){
		documentRepository.delete(documentId);
	}
	
	public Document get(Long id){

		return documentRepository.findOne(id);
	
	}
	
	public List<Document> getAllDocuments(){
		
		Iterable<Document> d = null;
		List<Document> documents = new ArrayList<Document>();
		try{
			d = documentRepository.findAll();
			
			System.out.println("DOCUMENTS COUNT: " + documentRepository.count());
		
			for(Document document: d){
				 documents.add(document);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return documents;
	}
	
	public List<Document> searchCriteria(String publicationName){
		List<Document> documents = null;
		
		try{
			publicationName = ".*"+publicationName+".*";
			documents = documentRepository.findByNames(publicationName);
			for(Document document: documents){
			System.out.println("List of documents info: " + document.getName() + "description:   " + document.getDescription()
					+ " Author: " + document.getAuthor());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return documents;
	}
	

}
