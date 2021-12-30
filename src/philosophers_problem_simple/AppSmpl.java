package philosophers_problem_simple;

import java.util.concurrent.Semaphore;

public class AppSmpl {
    public static void main(String[] args) {
        Semaphore sem = new Semaphore(2);
        for(int i=1;i<60;i++)
            new PhilosopherSmpl(sem,i).start();
    }
}
