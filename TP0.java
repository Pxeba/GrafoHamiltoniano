import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class TP0
{
   static int [][] grafo;
   static int tamanhoGrafo;
   static int numeroDeArestas;
   static int initVertice;
   static int [][] caminhos;
   static int tamanhoCaminhos;
   static int [][] caminhosReais;
   static int tamanhoReais;
   static int rotaMenor= 0;
   static int rotaDoChiquinho = 0;
   static int distanciaRotaMenor = 0;
   static int distanciaRotaChiquinho = 0;
   
   //instancia o grafo com todos valores null
   public static void grafoNull()
   {
      grafo = new int[tamanhoGrafo][tamanhoGrafo];
      for(int x = 0;x<tamanhoGrafo;x++)
      {
         for(int y = 0;y<tamanhoGrafo;y++)
         {
            grafo[x][y] = 0;
         }
      }
      numeroDeArestas = 0;
   }
   
   /*
   gera um grafo nao digrafo(um grafo normal)
   @param matriz int grafo nulo
   @param string nome do arquivo de texto lido
   */
   public static void grafoNaoDigrafo(int grafo[][],String nomeTxt)
   {
      try
      {
         String aresta = "";
         int cont = 0;
         FileReader arq = new FileReader(nomeTxt);
         BufferedReader lerArq = new BufferedReader(arq);
         //ler 2 primeiras linhas
         lerArq.readLine();
         lerArq.readLine();
         lerArq.readLine();
         aresta = lerArq.readLine();
         while(aresta.equals("FIM") == false)
         {
            
            int origem,destino,peso = 0;
           
            origem = aresta.charAt(0) - 48;
            destino = aresta.charAt(2) - 48;
            peso = aresta.charAt(4) - 48;
         
            
            grafo[origem][destino] = peso;
            grafo[destino][origem] = peso;
            numeroDeArestas++;
            
            aresta = lerArq.readLine();
            
         }
      }
      catch(IOException e)
      {
         System.out.println("Deu ruim aqui!");
      }
   }
   
   /*
   gera um grafo digrafo(arestas direcionais)
   @param matriz int grafo nulo
   @param string nome do arquivo de texto lido
   */
   public static void grafoDigrafo(int grafo[][],String nomeTxt)
   {
      try
      {
         String aresta = "";
         int cont = 0;
         FileReader arq = new FileReader(nomeTxt);
         BufferedReader lerArq = new BufferedReader(arq);
         //ler 2 primeiras linhas
         lerArq.readLine();
         lerArq.readLine();
         aresta = lerArq.readLine();
         while(aresta.equals("FIM") == false)
         {
            
            int origem,destino,peso = 0;
           
            origem = aresta.charAt(0);
            destino = aresta.charAt(2);
            peso = aresta.charAt(4);
         
            grafo[origem][destino] = peso;
            numeroDeArestas++;
            
            aresta = lerArq.readLine();
            
         }
      }
      catch(IOException e)
      {
      
      }   
   }
   //dado o numero do vertice retorna seu grau
   //@param int numero do vertice a ser calculado o grau
   public static int grauDeUmVertice()
   {
      System.out.println("Escolha um vertice para descobrir o grau do mesmo");
      Scanner ler = new Scanner(System.in);
      int vertice = Integer.parseInt(ler.nextLine());
      int cont = 0;
      for(int x = 0;x<tamanhoGrafo;x++)
      {
         if(grafo[vertice][x] != 0)
         {
            cont++;
         }
      }
   
      return cont;
   }
   
   //checa se o grafo e completo
   public static boolean grafoCompleto()
   {
      boolean completo = true;
      for(int x = 0;x<tamanhoGrafo;x++)
      {
         for(int y = 0;y<tamanhoGrafo;y++)
         {
            if(grafo[x][y] == 0)
            {
               completo = false;
               x = tamanhoGrafo;
               y = tamanhoGrafo;
            }
         }
      }
      return completo;
   }
   
   //torna grafo completo(se nao for)
   public static void completarGrafo()
   {
      if(grafoCompleto() == false)
      {
         for(int x = 0;x<tamanhoGrafo;x++)
         {
            for(int y = 0;y<tamanhoGrafo;y++)
            {
               if(grafo[x][y] == 0)
               {  
                  grafo[x][y] = 1;
               }
            }
         }
      }
      else
      {
         System.out.println("Grafo ja esta completo");
      }
   }
   
   //Metodo principal,le txt e escreve o grafo
   public static void gerarGrafo()
   {
      Scanner ler = new Scanner(System.in);
      System.out.printf("Informe o nome de arquivo texto:\n");
      try
      {
         String nome = ler.nextLine();
         FileReader arq = new FileReader(nome);
         BufferedReader lerArq = new BufferedReader(arq);
         char linha1 = lerArq.readLine().charAt(0); //primeira linha (Digrafo ou Grafo)
         tamanhoGrafo = Integer.parseInt(lerArq.readLine()); //segunda linha (Numero de Vertices)
         initVertice = Integer.parseInt(lerArq.readLine()); //terceira linha (vertice inicial)
         grafoNull(); //gera grafo instanciado no global[][]
         
         //NaoDigrafo
         if(linha1 == '0')
         {
            grafoNaoDigrafo(grafo,nome);
         }
         else 
         {
            //Digrafo
            if(linha1 == '1')
            {
               grafoDigrafo(grafo,nome);
            }
            else
            {
               System.out.print("erro com linha 1");
            }
         
         } 
         
      }
      catch(IOException e)
      {
         System.err.printf("Erro na abertura do arquivo: %s.\n",
            e.getMessage());
      }
   
   }
   
   public static void initCaminhos()
   {
      for(int cont = 0;cont < 10000;cont++)
      {
         for(int cont2 = 0;cont2<tamanhoGrafo;cont2++)
         { 
            caminhos[cont][cont2] = -1;
         }
      }
   }
   
   public static void percorrer()
   {
      int [] verticesPassados = new int[tamanhoGrafo];
      int tamanhoLista = 0;
      caminhos = new int [10000][tamanhoGrafo];
      caminhosReais = new int [10000][tamanhoGrafo];
      initCaminhos();
      tamanhoCaminhos = 0;
      tamanhoReais = 0;
      percorrerRecursivo(initVertice,verticesPassados,tamanhoLista);
   }
   
   public static  void percorrerRecursivo(int verticeAtual, int [] verticesPassados,int posicaoLista)
   {
      if(posicaoLista == tamanhoGrafo)
      {
         //printarVerticesPassados(verticesPassados,posicaoLista);
      
            //printarVerticesPassados(verticesPassados,posicaoLista);
         for(int x = 0;x<tamanhoGrafo;x++)
         {
            caminhos[tamanhoCaminhos][x] = verticesPassados[x];
            
         }
         tamanhoCaminhos++;
            //System.out.println(tamanhoCaminhos);
      }
      else
      {
         //printarVerticesPassados(verticesPassados,posicaoLista);
         
         for(int vertices = 0;vertices < tamanhoGrafo; vertices++)
         {
            //System.out.print(vertices);
            if(grafo[verticeAtual][vertices] > 0)
            {
               int [] tmp = adicionar(verticesPassados,posicaoLista,vertices);
               //printarVerticesPassados(verticesPassados,posicaoLista);
               //System.out.println(tmp[3]);
               percorrerRecursivo(vertices,tmp,posicaoLista+1);
            }
            
            
         }
         //printarVerticesPassados(verticesPassados,posicaoLista);
      }
   }
   
   
   public static void checarVertices()
   {
      boolean test = true;
      for(int x = 0;x< tamanhoCaminhos;x++)
      {
         test = true;
         for(int y = 0;y < tamanhoGrafo;y++)
         {
            int cont = 0;
            for(int z = 0;z<tamanhoGrafo;z++)
            {
               if(caminhos[x][z] == y)
               {
                  cont++;
               }
            }
            if(cont > 1 || caminhos[x][tamanhoGrafo-1] != initVertice)
            {
               test = false;  
            }
         }
         if(test)
         {
            for(int y = 0;y<tamanhoGrafo;y++)
            {
               caminhosReais[tamanhoReais][y] = caminhos[x][y];
            
            }
            tamanhoReais++;
         
         }
         
      };
   }
   
   public static void menorDistancia()
   {
      int menorCaminho = 100000;
      int numeroDaRota = 0;
      for(int cont = 0 ;cont < tamanhoReais ;cont++)
      {
         int caminho = grafo[initVertice][caminhosReais[cont][0]];
         for(int cont2 = 0;cont2 < tamanhoGrafo-1;cont2++)
         {
            caminho = caminho + grafo[caminhosReais[cont][cont2]][caminhosReais[cont][cont2+1]];
         }
         
         if(caminho < menorCaminho)
         {
            //System.out.println("a");
            menorCaminho = caminho;
            numeroDaRota = cont;
         }
      }
      distanciaRotaMenor = menorCaminho;
      rotaMenor = numeroDaRota;
      // System.out.println(menorCaminho);
      // System.out.println(numeroDaRota);
      // System.out.println(tamanhoReais);
   }
   
   public static void chiquinhoDistancia()
   {
      int menorRota = 10000;
      int caminho = 0;
      int [] rotasPossiveis = new int[tamanhoReais];
      
      //inicializar com negativo
      for(int cont = 0;cont < tamanhoReais;cont++)
      {
         rotasPossiveis[cont] = -1;
      }
      
      int [] rotaChiquinho = new int[tamanhoGrafo];
      int contRotas = 0;
      
      //definir menor rota inicial
      for(int x = 0;x<tamanhoReais;x++)
      {
         caminho = grafo[initVertice][caminhosReais[x][0]];
         if(caminho <= menorRota)
         {
            menorRota = caminho;
         }
      }
      
      //System.out.println(menorRota);
      
      for(int x = 0;x<tamanhoReais;x++)
      {
         caminho = grafo[initVertice][caminhosReais[x][0]];
         if(caminho == menorRota)
         {
            rotasPossiveis[contRotas] = x;
            contRotas++;
         }
      }
   
     
      caminho = 0;
      menorRota = 10000;
      int rotasExcluidas = 0;
      // System.out.println(contRotas);
      // System.out.println(rotasPossiveis[0]);
      // System.out.println(rotasPossiveis[1]);
      // System.out.println(rotasPossiveis[2]);
      // System.out.println(rotasPossiveis[3]);
      // System.out.println(rotasPossiveis[4]);
      for(int cont2 = 0;cont2 < tamanhoGrafo-1;cont2++)
      {
         if(rotasExcluidas == contRotas-1)
         {
            cont2 = tamanhoGrafo-1;//terminar
         }
         else
         {
            for(int cont = 0;cont<contRotas; cont++)
            {
               if(rotasExcluidas == contRotas-1)
               {
                  cont = contRotas;//terminar
               }
               else
               {
                  if(rotasPossiveis[cont] != -1)
                  {
                     int v1 =  caminhosReais[rotasPossiveis[cont]][cont2];//vertice 1
                     int v2 =  caminhosReais[rotasPossiveis[cont]][cont2+1];//vertice 2
                     caminho = grafo[v1][v2];//distancia dos 2 vertices
                     if(caminho <= menorRota)
                     {
                        menorRota = caminho;
                     }
                     else
                     {
                        rotasPossiveis[cont] = -1;//excluir possivel caminho
                        rotasExcluidas++;
                     }
                  }
               }
            
            }
         }
      }
      //procurar por caminhos restantes
      for(int cont = 0;cont< contRotas;cont++)
      {
         
         if(rotasPossiveis[cont] != -1)
         {
            //System.out.println("sucess");
            rotaDoChiquinho = rotasPossiveis[cont];
         }
         
      }
      //distancia final chiquinho!
      int distanciaChiquinho = 0;
      distanciaChiquinho = grafo[initVertice][caminhosReais[rotaDoChiquinho][0]];//distancia do primeiro vertice
      for(int cont = 0;cont < tamanhoGrafo-1;cont++)
      {
         int v1 = caminhosReais[rotaDoChiquinho][cont];
         int v2 = caminhosReais[rotaDoChiquinho][cont+1];
         distanciaChiquinho = distanciaChiquinho + grafo[v1][v2];
      }
      distanciaRotaChiquinho = distanciaChiquinho;//passando para variavel global
   }
   
   public static void mostrarRotasNaTela()
   {
      String out = initVertice + "-";
      for(int cont = 0;cont < tamanhoGrafo;cont++)
      {
         if(cont != tamanhoGrafo-1)
         {
            out = out + caminhosReais[rotaMenor][cont] + "-";   
         }
         else
         {
            out = out + caminhosReais[rotaMenor][cont] + ";";
         }
      }
      out = out + "" + distanciaRotaMenor;
      System.out.print(out);
   }
   
   public static void mostrarRotasNaTelaChiquinho()
   {
      String out = initVertice + "-";
      for(int cont = 0;cont < tamanhoGrafo;cont++)
      {
         if(cont != tamanhoGrafo-1)
         {
            out = out + caminhosReais[rotaDoChiquinho][cont] + "-";   
         }
         else
         {
            out = out + caminhosReais[rotaDoChiquinho][cont] + ";";
         }
      }
      out = out + "" + distanciaRotaChiquinho;
      System.out.print(out);
   }
   
  
   public static void printarVerticesPassados(int [] verticesPassados,int tamanho)
   {
      for(int x = 0;x<tamanho;x++)
      {
         System.out.print(verticesPassados[x]);
      }
      System.out.println();
   }
   
   public static boolean buscar(int [] lista,int vertice)
   {
      for(int x = 0;x<tamanhoGrafo;x++)
      {
         if(lista[x] == vertice)
         {
            return true;
         }
      }
      return false;
   }
   
   public static int [] adicionar(int [] lista,int tamanhoLista,int vertice)
   {
      //System.out.println(tamanhoLista);
      lista[tamanhoLista] = vertice;
      return lista;
   
   }
   
   public static void vizualizarCaminhos()
   {
      
      for(int cont = 0;cont<tamanhoCaminhos;cont++)
      {
         for(int cont2 = 0;cont2 < 5;cont2++)
         {
            System.out.print(caminhos[cont][cont2]);
         }
         System.out.println();
      }
     
   }
   
   public static void vizualizarCaminhos2()
   {
      for(int cont = 0;cont<tamanhoReais;cont++)
      {
         for(int cont2 = 0;cont2 < 5;cont2++)
         {
            System.out.print(caminhosReais[cont][cont2]);
         }
         System.out.println();
      }
     
   }
   

   public static void main(String[]args)
   {
      gerarGrafo();
      percorrer();
      checarVertices();
      //System.out.println(tamanhoReais);
      //vizualizarCaminhos2();
      menorDistancia();
      chiquinhoDistancia();
      mostrarRotasNaTelaChiquinho();
      System.out.println();
      mostrarRotasNaTela();
      
      
      
   }
}