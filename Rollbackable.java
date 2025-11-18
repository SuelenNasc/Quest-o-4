public interface Rollbackable {
    void rollback(ValidationContext context);
}