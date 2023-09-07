package url.get.lambda;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
class LambdaMethodHandlerTest {
    @Test
    public void shouldReturnHelloMessage(){
        LambdaMethodHandler sut = new LambdaMethodHandler();
        Assertions.assertEquals("Hello World", sut.handlerRequest());
    }
  
}