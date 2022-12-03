public class Filosofos implements Runnable {
    int id;
    static boolean[] tenedores = new boolean[5];

    public Filosofos(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        int tenedorIzquierda = id;
        int tenedorDerecha = id + 1;
        if (tenedorDerecha == 5) {
            tenedorDerecha = 0;
        }
        while (true) {
            try {

                synchronized (tenedores){
                    //Esperamos a que los tenedores esten libres
                    while (tenedores[tenedorDerecha]||tenedores[tenedorIzquierda]){
                        tenedores.wait();
                    }
                    //El filosofo coge los tenedores
                    tenedores[tenedorDerecha]=true;
                    System.out.println("El filosofo "+id+" cogio el tenedor derecho "+tenedorDerecha);
                    tenedores[tenedorIzquierda]=true;
                    System.out.println("El filosofo "+id+" cogio el tenedor izquierdo "+tenedorIzquierda);
                }
                //El filosofo come
                Thread.sleep((int)(Math.random()*4000));
                synchronized (tenedores){
                    //El filosofo suelta los dos tenedores
                    tenedores[tenedorDerecha]=false;
                    tenedores[tenedorIzquierda]=false;
                    tenedores.notifyAll();
                    System.out.println("El filosofo "+id+" solto los tenedores "+tenedorDerecha+" "+tenedorIzquierda);
                }
                //El filosofo piensa
                Thread.sleep((int)(Math.random()*4000));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        //Lanzamos a los 5 filosofos
        for (int i = 0; i < 5; i++) {
            new Thread(new Filosofos(i)).start();
        }
    }
}