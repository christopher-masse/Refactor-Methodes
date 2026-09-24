package main.java.app;

public class SingletonDemo {
    private static SingletonDemo instance;
    private int difficulte;

    private SingletonDemo() {
        this.instance = new SingletonDemo();
        this.difficulte = 1;
    }

    public static SingletonDemo getInstance() {
        if (instance != null) {
            return instance;
        } else {
            return new SingletonDemo();
        }
    }

    public int getDifficulte() {
        return difficulte;
    }

    public void setDifficulte(int difficulte) {
        this.difficulte = difficulte;
    }




}
