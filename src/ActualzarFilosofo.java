public class ActualzarFilosofo implements Runnable{
    /**
     * Notificara a los filosfos cada 10 sec
     */
    @Override
    public void run() {
        Long tiempo=System.currentTimeMillis();
        while (true){
            if(System.currentTimeMillis()-tiempo>10000){
                synchronized (Filosofos.filosofos) {
                    Filosofos.filosofos.notifyAll();
                }
                tiempo=System.currentTimeMillis();
            }
        }
    }
}
