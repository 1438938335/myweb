import com.wyy.Application;
import org.junit.After;
import org.junit.Before;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = Application.class)
public class ApplicationTest
{
    private long t1;
    private long t2;

    @Before
    public void before()
    {
        t1 = System.currentTimeMillis();
    }

    @After
    public void after()
    {
        t2 = System.currentTimeMillis();
        System.out.println("程序耗时:" + (t2 - t1));
    }
}
