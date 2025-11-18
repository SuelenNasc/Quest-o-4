import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ValidationContext {
    private final NFe nfe;
    private final List<String> failures = new ArrayList<>();
    private final Stack<Rollbackable> rollbackStack = new Stack<>();

    public ValidationContext(NFe nfe) {
        this.nfe = nfe;
    }

    public NFe getNfe() { return nfe; }

    public void addFailure(String failureMessage) {
        this.failures.add(failureMessage);
    }
    
    public boolean hasFailures() {
        return !this.failures.isEmpty();
    }
    
    public int getFailureCount() {
        return this.failures.size();
    }
    
    public List<String> getFailures() {
        return this.failures;
    }

    public void pushRollbackCommand(Rollbackable command) {
        this.rollbackStack.push(command);
    }

    public void executeRollbacks() {
        while (!rollbackStack.isEmpty()) {
            Rollbackable command = rollbackStack.pop();
            command.rollback(this);
        }
    }
}