package finance.processors;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import finance.models.Transaction;
import finance.services.TransactionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
//@ComponentScan(basePackages = "finance")
public class JsonTransactionProcessorTest {
    private ResourceBundle resourceBundle;
    private static ObjectMapper mapper = new ObjectMapper();
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @MockBean
    TransactionService transactionService;

    private Transaction transactionDummy;
    private Transaction[] transactionsDummy;
    String json_string;

    @Before
    public void setup() throws JsonParseException, IOException {
        json_string = "{\"guid\":\"83e30475-fcfc-4f30-8395-0bb40e89b568\",\"sha256\":\"b154020e5cd52d086d1650fa01a82b82f823e67cd59b7b346aeeb781c7dafa44\",\"accountType\":\"credit\",\"accountNameOwner\":\"chase_brian\",\"description\":\"First Transaction\",\"category\":\"\",\"notes\":\"Something\",\"cleared\":1,\"reoccurring\":false,\"amount\":\"0.1\",\"transactionDate\":1353456000,\"dateUpdated\":1538351596,\"dateAdded\":1538351596}";
        transactionDummy = mapper.readValue(json_string, Transaction.class);
        String guid = "83e30475-fcfc-4f30-8395-0bb40e89b568";
//        transactionDummy = new Transaction();
//        transactionDummy.setAccountNameOwner("chase_brian");
//        transactionDummy.setAccountType("credit");
//        transactionDummy.setAmount(0.01);
//        transactionDummy.setCategory("none");
//        transactionDummy.setCleared(1);
//        transactionDummy.setDescription("test example");
//        transactionDummy.setNotes("");
        //this.resourceBundle = PropertyResourceBundle.getBundle("test_inputs");
        MockitoAnnotations.initMocks(this);
        //Mockito.doNothing().when(transactionService).insertTransaction(transactionDummy);
        //Mockito.doNothing().when(transactionService).findByGuid(guid);
    }

    @Test
    public void dummy()  {
        //assertEquals(transactionDummy.getGuid(), "0e0a01e2-06b0-4e3f-a15c-5afa86d968ce");
        assert (true);
    }
}