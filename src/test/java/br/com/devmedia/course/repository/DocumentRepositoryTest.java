package br.com.devmedia.course.repository;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.devmedia.course.CourseSpringDataApplication;
import br.com.devmedia.course.entity.Document;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CourseSpringDataApplication.class)
public class DocumentRepositoryTest {
    private Document documentOne;
    private Document documentTwo;
    private Document documentThree;
    
    @Autowired
    private DocumentRepository documentRepository;

    @Before
    public void setUp() {
        this.documentOne = new Document("987.654.321-85", "58.963.741-58");
        this.documentTwo = new Document("321.654.987-69", "36.547.852-98");
        this.documentThree = new Document("741.852.963.25", "54.962.871-25");
        
        this.documentRepository.save(this.documentOne);
        this.documentRepository.save(this.documentTwo);
        this.documentRepository.save(this.documentThree);
    }

    @Test
    public void shouldFindDocumentsByCPFStartWith() {
        List<Document> documents = this.documentRepository.findByCPFStartWith("987.654");
        Assert.assertFalse(documents.isEmpty());
        Assert.assertEquals(1, documents.size());
        for (Document document : documents) {
            Assert.assertTrue(document.getCpf().startsWith("987.654"));
        }
    }
}