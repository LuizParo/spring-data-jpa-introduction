package br.com.devmedia.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import br.com.devmedia.course.entity.Phone;
import br.com.devmedia.course.entity.Phone.TypePhone;

@Transactional(readOnly = true)
public interface PhoneRepository extends JpaRepository<Phone, Long> {
    
    @Modifying
    @Transactional(readOnly = false)
    @Query("update Phone p set p.number = ?1 where p.id = ?2")
    public int setPhoneNumber(String number, Long id);
    
    @Modifying
    @Transactional(readOnly = false)
    @Query("update Phone p set p.type = ?1 where p.id = ?2")
    public int setPhoneType(TypePhone type, Long id);
    
    @Modifying
    @Transactional(readOnly = false)
    @Query("delete from Phone p where p.number = ?1")
    public int removePhoneByNumber(String number);
}