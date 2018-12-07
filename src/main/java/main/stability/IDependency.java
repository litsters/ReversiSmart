package main.stability;

public interface IDependency {
    public boolean updateRequirements(BoardCell neighbor); // Returns true if after updating the requirements are all met for stability
}
