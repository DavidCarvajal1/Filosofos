public class Filosofos implements Runnable {
    int id;
    static boolean[] filosofos=new boolean[5];
    public Filosofos(int id){
        this.id=id;
    }
    @Override
    public void run() {
        while (true) {
            try {
                int tenedorIzquierda=id;
                int tenedorDerecha=id+1;
                if(tenedorDerecha==5){
                    tenedorDerecha=0;
                }
                //Intentamos coger el primer tenedor
                synchronized (filosofos) {
                    while (filosofos[tenedorIzquierda]) {
                        filosofos.wait();
                    }
                    filosofos[tenedorIzquierda] = true;
                }
                System.out.println("El filosofo " + id + " cogio el tenedor izquierdo "+tenedorIzquierda);
                //Intentamos coger el segundo tenedor
                synchronized (filosofos) {
                    while (filosofos[tenedorDerecha]) {
                        filosofos.wait();
                    }
                    filosofos[tenedorDerecha] = true;
                }
                System.out.println("El filosofo " + id + " cogio el tenedor derecho "+(tenedorDerecha));

                Thread.sleep((int) (Math.random() * 3000) + 2000);
                //Soltamos ambos libros
                synchronized (filosofos) {
                    filosofos[tenedorIzquierda] = false;
                    filosofos[tenedorDerecha] = false;
                    filosofos.notifyAll();
                }
                System.out.println("El filosofo " + id + " soloto los tenedores "+tenedorIzquierda+" "+(tenedorDerecha));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }




    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(new Filosofos(i)).start();
        }
    }
}