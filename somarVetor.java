import java.util.ArrayList;
import java.util.Random;

class CriacaoAuxiliar {
    //recurso compartilhado
    private int somatorio;

    /*Função que faz a soma dos valores do vetor 
    dividindo em blocos com tamanho igual a Threads 
    que estão operando no sistema. */
    public synchronized void somar(int[] v, int t, int nthreads) {
        
        int id = t;
        int tam = v.length/nthreads;
        int ini = id*tam;
        int fim = ini + tam;
        if(id == nthreads -1) fim = v.length;
        for(int i = ini; i < fim; i++){
            somatorio += v[i];
            //System.out.println("A thread " + id + " que está somando agora" );
        }
    }

    //Função que faz o print do somatorio
    public synchronized int mostrarSomatorio() { 
        return this.somatorio; 
    }
    
  }
  
  
class CriacaoPrincipal extends Thread {
    private int id, thread;
    int[] vetor;
    CriacaoAuxiliar c;

    //--construtor
    public CriacaoPrincipal(int tid, CriacaoAuxiliar aux, int[] V, int r){ 
       vetor = new int[V.length];
       this.id = tid;
       this.c = aux;
       this.thread = r;
        for(int i = 0; i< V.length; i++){
           vetor[i] = V[i];
       }
    }

    //--metodo executado pela thread
    public void run() {
        System.out.println("Thread " + this.id + " iniciou!");
        this.c.somar(vetor, id, thread);
        System.out.println("Thread " + this.id + " terminou!");
    }
 }
 

class somaVetor {

    static final int N = 3;
    static final int total = 10;
    public static void main(String[] args) {
        //reserva espaço para a criação de um vetor de inteiros aleatorios e de threads
        Thread[] threads = new Thread[N];
        int[] vetor = new int[total];
        
        //cria uma instancia do recurso compartilhado entre as threads
        CriacaoAuxiliar c = new CriacaoAuxiliar();
    
        //Criação do vetor de valores aleatorios
        for(int i = 0; i < total; i++){
            Random aleatorio = new Random();
            vetor[i] = aleatorio.nextInt(11);
        }
        //Print do vetor que será trabalhado
        System.out.print("[");
         for(int i = 0; i < total; i++){
            System.out.print(" " + vetor[i] + " ");
        }
        System.out.print("]");
        System.out.print("\n");

        //Criação das Threads
        for (int i=0; i<threads.length; i++) {
            //final String m = "Ola da thread " + i;
            threads[i] = new CriacaoPrincipal(i, c, vetor, N);
         }
   
        //Faz com que as Threads iniciem
        for(int i = 0; i < threads.length; i++){
            threads[i].start();
        }

        //faz o fluxo do programa esperar todas as threads terminarem 
        for(int i = 0; i < threads.length; i++){
        try{
            threads[i].join();
        }catch (InterruptedException e ){
            return;}
        }
        
        System.out.println("Valor de s = " + c.mostrarSomatorio()); 

    }
}