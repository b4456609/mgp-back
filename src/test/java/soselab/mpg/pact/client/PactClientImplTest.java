package soselab.mpg.pact.client;

//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PactClientImplTest {

    private PactClient pactClient = new PactClientImpl();

//    @Test
//    public void getPactFileByProviderAndConsumer() throws Exception {
//        String pactFileByProviderAndConsumer = pactClient.getPactFileByProviderAndConsumer("http://140.121.102.161:8880/pacts/latest"
//                , "easylearn_user", "easylearn_pack");
//        assertThat(pactFileByProviderAndConsumer)
//                .contains("easylearn_user");
//        assertThat(pactFileByProviderAndConsumer)
//                .contains("easylearn_pack");
//        assertThat(pactFileByProviderAndConsumer)
//                .contains("interaction");
//    }

}