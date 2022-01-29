package pgdp.stream.exceptions;

public class ErrorsAtTerminalOperationException extends RuntimeException{
    public ErrorsAtTerminalOperationException(){
        super();
    }
    public ErrorsAtTerminalOperationException(String string){
        super(string);
    }

}
