package io.berivan.couse.unittest;

import io.Contact;
import io.Kure;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StudentTestWithTestLifecyle
{
    private Student mehmet = new Student("mehmet", "mehmet", "1");

    @BeforeAll
    static  void  setUp(){
        // statici kaldırıp Per class yaparsak hata almayacağız.
    }

    @Contact(Kure.Bireysel)
    @Test
    void testPerMethod()
    {
        assertEquals("mehmet", mehmet.getName());
        mehmet = new Student("ahmet", "ahmet"    , "1");

    }

    @Contact(Kure.Bireysel)
    @Test
    void testPerClass()
    {
        assertEquals("mehmet", mehmet.getName());
        mehmet = new Student("ahmet", "ahmet"    , "1");

    }
//per method olduğunda iki test pass olacak.
// per class olduğunda herhangi bir testteki assertions patlayacak. Junit order garantisi vermiyor Bu yüzde hangi testin önce çalışağını bilmiyoruz. Bit tane test instancesi var. Bir yerde yaptığın değişiklik diğer metotlarda görülüyor.

}
