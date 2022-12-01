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
                long tiempoDeEspera;
                boolean tiempoDeEsperaSuperado=false;
                //Intentamos coger el primer tenedor
                synchronized (filosofos) {
                    while (filosofos[tenedorIzquierda]) {
                        filosofos.wait();
                    }
                    filosofos[tenedorIzquierda] = true;
                }
                System.out.println("El filosofo " + id + " cogio el tenedor izquierdo "+tenedorIzquierda);
                //Intentamos coger el segundo tenedor
                //Si espera mas de X tiempo, soltara el primer tenedor, sino cogera el derecho
                synchronized (filosofos) {
                    tiempoDeEspera=System.currentTimeMillis();
                    while (filosofos[tenedorDerecha]&&!tiempoDeEsperaSuperado) {
                        filosofos.wait();
                        if(System.currentTimeMillis()-tiempoDeEspera>10000){
                            tiempoDeEsperaSuperado=true;
                        }
                    }
                    if(!filosofos[tenedorDerecha]) {
                        filosofos[tenedorDerecha] = true;
                        System.out.println("El filosofo " + id + " cogio el tenedor derecho "+(tenedorDerecha));
                        Thread.sleep((int) (Math.random() * 3000) + 2000);
                        //Soltamos ambos libros
                        synchronized (filosofos) {
                            filosofos[tenedorIzquierda] = false;
                            filosofos[tenedorDerecha] = false;
                            filosofos.notifyAll();
                        }
                        System.out.println("El filosofo " + id + " soloto los tenedores "+tenedorIzquierda+" "+(tenedorDerecha));

                    }else{
                        filosofos[tenedorIzquierda]=false;
                        System.out.println("El filosofo "+id+" solto los tenedor por tiempo de espera superado");
                    }
                }


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
        //Lanzamos la clase que notificara a los filosofos cada 10 sec
        new Thread(new ActualzarFilosofo()).start();
    }
}