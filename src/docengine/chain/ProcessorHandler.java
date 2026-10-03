package docengine.chain;


public abstract class ProcessorHandler {

    private ProcessorHandler next;


    public ProcessorHandler setNext(ProcessorHandler next) {
        this.next = next;
        return next;
    }

    public final void process(ProcessingContext context) {
        handle(context);
        if (!context.isStopped() && next != null) {
            next.process(context);
        }
    }

    protected abstract void handle(ProcessingContext context);

    public abstract String getName();
}
