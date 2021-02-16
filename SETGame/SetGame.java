import java.awt.*;
import java.awt.event.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

public class SetGame extends JPanel implements MouseListener{

    private static final long serialVersionUID = 1L;

    //the decks that will store the set cards
    ArrayList<SetCard> dealerDeck;
    SetCard [] inPlayDeck;
    ArrayList<SetCard> discardDeck;

    //indexes of the cards picked
    int i1, i2, i3;

    public SetGame(){
        //////////////////////////////////////////////////////////////////////////////////////
        //Create the decks using the new operator
        dealerDeck = new ArrayList<SetCard>();
        discardDeck = new ArrayList<SetCard>();
        inPlayDeck = new SetCard[12];

        //////////////////////////////////////////////////////////////////////////////////////
        //Create card for the dealer and shuffle
        createCardsForDealer();
        shuffle();

        //////////////////////////////////////////////////////////////////////////////////////
        //Deal cards and set indexes
        for(int n = 0; n < 12; n++){
            inPlayDeck[n] = dealerDeck.remove(0);
        }

        i1 = -1;
        i2 = -1;
        i3 = -1;

        setLayout(null);
        setVisible(true);
        addMouseListener(this);

    }

    public void shuffle(){
        ArrayList<SetCard> shuffle;
        shuffle = new ArrayList<SetCard>();
        while(dealerDeck.size() != 0){
            shuffle.add(dealerDeck.remove((int)(dealerDeck.size()*Math.random())));
        }

        dealerDeck = shuffle;
    }
    //////////////////////////////////////////////////////////////////////////////////////
    //Name:  createCardsForDealer
    //Input:  nothing
    //Output:  nothing
    //Action:  creates one of each type of card for the dealer deck

    public void createCardsForDealer(){
        for(int i = 1; i <= 3; i++){
            for(int j = 1; j <= 3; j++){
                for(int k = 1; k <= 3; k++){
                    for(int l = 1; l <= 3; l++){
                        dealerDeck.add(new SetCard(i, j, k , l));
                    }
                }
            }
        }

    }

    //////////////////////////////////////////////////////////////////////////////////////
    //Name:  pickCard
    //Input:  index of the next card picked
    //Output:  nothing
    //Action:  takes in the index of the next card picks and if it is the 3rd one then it checks for a match
    public void pickCard(int index){
        System.out.println("i1 : " + i1 + ", i2 : " + i2  + ", i3 : " + i3); 
        if(i1 == -1 || i1 == index){
            i1 = index;
        } else if(i2 == -1 || i2 == index) {
            i2 = index;
        } else if(i3 == -1) {
            i3 = index;
            if(match(inPlayDeck[i1], inPlayDeck[i2], inPlayDeck[i3]) == true){
                discardDeck.add(inPlayDeck[i1]);
                discardDeck.add(inPlayDeck[i2]);
                discardDeck.add(inPlayDeck[i3]);
                inPlayDeck[i1] = dealerDeck.remove(0);
                inPlayDeck[i2] = dealerDeck.remove(0);
                inPlayDeck[i3] = dealerDeck.remove(0);
            }
            i1 = -1;
            i2 = -1;
            i3 = -1;
        }

    }

    //////////////////////////////////////////////////////////////////////////////////////
    //Name:  match
    //Input:  3 set cards
    //Output:  whether the three cards constitute a SET
    //Action:  takes in three SET Cards and returns whether those cards form a SET
    public boolean match(SetCard card1, SetCard card2, SetCard card3){
        if(card1.getColor() != (card2.getColor()) && card1.getColor() == (card3.getColor()) || (card1.getColor() == (card2.getColor()) && card1.getColor() != (card3.getColor()))){
            return false;
        }
        if(card1.getFill() != (card2.getFill()) && card1.getFill() == (card3.getFill()) || card1.getFill() == (card2.getFill()) && card1.getFill() != (card3.getFill())){
            return false;
        }
        if(card1.getShape() != (card2.getShape()) && card1.getShape() == (card3.getShape()) || (card1.getShape() == (card2.getShape()) && card1.getShape() != (card3.getShape()))){
            return false;
        }
        if(card1.getNumber() != (card2.getNumber()) && card1.getNumber() == (card3.getNumber()) || (card1.getNumber() == (card2.getNumber()) && card1.getNumber() != (card3.getNumber()))){
            return false;
        }
        return true;

    }

    //////////////////////////////////////////////////////////////////////////////////////
    //Name:  howMany
    //Input:  nothing
    //Output:  how many sets in are currently in play
    //Action:  goes through all the cards in the inPlayDeck and determines how many SETS can be made from them
    public int howMany(){
        return 0;
    }

    public void mousePressed(MouseEvent e){
        int xCoord = e.getX();
        int yCoord = e.getY();
        //System.out.println("Clicked X: " + xCoord + ", Y: " + yCoord);
        int index = xCoord/200 + 4 * (yCoord/150);
        //System.out.println("Index of Card Just Picked: " + index);
        if(index < 12){

            pickCard(index);
        }
        repaint();
    }

    public void mouseReleased(MouseEvent e){

    }

    public void mouseClicked(MouseEvent e){

    }

    public void mouseEntered(MouseEvent e){

    }

    public void mouseExited(MouseEvent e){

    }   

    public static void main(String [] arg){
        JFrame frame;
        frame = new JFrame("SET");          
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);           
        frame.setLocationRelativeTo(null);          
        frame.setSize(820, 800);   
        frame.setLocation(0, 0);
        frame.setVisible(true);         

        SetGame game = new SetGame();           
        frame.getContentPane().add(game);           
        frame.setVisible(true);

        frame.repaint();
    }

    public void drawInPlay(Graphics g){
        int x = 0;
        int y = -75;

        for(int index = 0; index < inPlayDeck.length; index ++){
            if(index % 4 == 0){
                x = 100;
                y += 150;
            }else{
                x += 200;
            }
            if(i1 == index || i2 == index || i3 == index){
                g.setColor(Color.ORANGE);
                g.fillRect(x-100, y-75, 200, 150);  
            }
            inPlayDeck[index].draw(g, x, y);
        }
    }

    public void drawDecks(Graphics g){
        g.setColor(Color.GRAY);
        g.fillRect(200, 500, 150, 200);
        g.fillRect(400, 500, 150, 200);
        String dealer = "Dealer Deck: " + dealerDeck.size();
        String discard = "Discard Deck: " + discardDeck.size();
        String numSets = "Number of Sets in Play: " + howMany();
        g.setColor(Color.ORANGE);
        g.drawString(dealer, 200, 499);
        g.drawString(discard, 400, 499);
        g.drawString(numSets, 300, 480);
    }

    public void paint(Graphics g){
        
        System.out.println("i1 : " + i1 + ", i2 : " + i2  + ", i3 : " + i3); 
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 1000, 1000);
        g.setColor(Color.GRAY);
        g.drawLine(0, 150, 810, 150);
        g.drawLine(0, 300, 810, 300);
        g.drawLine(0, 450, 810, 450);
        g.drawLine(200, 0, 200, 450);
        g.drawLine(400, 0, 400, 450);
        g.drawLine(600, 0, 600, 450);

        drawDecks(g);
        drawInPlay(g);
    }

}
