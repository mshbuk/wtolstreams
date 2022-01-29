package pgdp.stream.exceptions;

public class CheckedStreamException extends RuntimeException{
    public CheckedStreamException(){
        super();
    }
    public CheckedStreamException(String string){
        super(string);
    }
}
