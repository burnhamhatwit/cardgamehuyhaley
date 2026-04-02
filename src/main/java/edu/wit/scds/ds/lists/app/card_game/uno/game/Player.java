

// @formatter:off
// you will modify this code - talk to me if you have any questions
// NOTE some of these methods have been fully modified for Uno - some are still Top This! logic
// @formatter:on


/* @formatter:off
 *
 * © David M Rosenberg
 *
 * Topic: List App ~ Card Game
 *
 * Usage restrictions:
 *
 * You may use this code for exploration, experimentation, and furthering your
 * learning for this course. You may not use this code for any other
 * assignments, in my course or elsewhere, without explicit permission, in
 * advance, from myself (and the instructor of any other course).
 *
 * Further, you may not post (including in a public repository such as on github)
 * nor otherwise share this code with anyone other than current students in my
 * sections of this course.
 *
 * Violation of these usage restrictions will be considered a violation of
 * Wentworth Institute of Technology's Academic Honesty Policy.  Unauthorized posting
 * or use of this code may also be considered copyright infringement and may subject
 * the poster and/or the owners/operators of said websites to legal and/or financial
 * penalties.  My students are permitted to store this code in a private repository
 * or other private cloud-based storage.
 *
 * Do not modify or remove this notice.
 *
 * @formatter:on
 */


package edu.wit.scds.ds.lists.app.card_game.uno.game ;

import edu.wit.scds.ds.lists.app.card_game.universal_base.support.NoCardsException ;
import edu.wit.scds.ds.lists.app.card_game.uno.card.Card ;
import edu.wit.scds.ds.lists.app.card_game.uno.card.Card.CompareOn ;
import edu.wit.scds.ds.lists.app.card_game.uno.card.CardColor ;
import edu.wit.scds.ds.lists.app.card_game.uno.card.CardValue ;
import edu.wit.scds.ds.lists.app.card_game.uno.pile.Deck ;
import edu.wit.scds.ds.lists.app.card_game.uno.pile.Hand ;
import edu.wit.scds.ds.lists.app.card_game.uno.pile.Pile ;
import edu.wit.scds.ds.lists.app.card_game.uno.pile.Stock ;

import static edu.wit.scds.ds.lists.app.card_game.uno.card.Card.newCardLike ;

import static edu.wit.scds.ds.lists.app.card_game.uno.pile.Pile.newTransferPile ;

import java.io.File ;
import java.io.FileNotFoundException ;
import java.util.Random ;
import java.util.Scanner ;

// you will modify this code - talk to me if you have any questions

/**
 * Representation of a player
 *
 * @author Dave Rosenberg
 *
 * @version 1.0 2025-11-11 Initial implementation based upon {@code top_this.game.Player}
 * @version 0.2 2026-03-20 more cleanup from Top This! to Uno
 * @version 0.3 2026-03-26 replace anonymous pile with factory invocation
 */
public final class Player
    {

    /*
     * data fields
     */


    /** player's name */
    public final String name ;

    /** the cards that are in-play */
    private final Hand hand ;

    /** number of points accumulated */
    private int points ;

    /** number of rounds we've won */
    private int roundsWon ;


    /*
     * constructor(s)
     */


    /**
     * initialize a player
     *
     * @param playerName
     *     the player's name
     */
    public Player( final String playerName )
        {

        this.name = playerName ;

        this.hand = new Hand() ;

        this.points = 0 ;
        this.roundsWon = 0 ;

        }   // end constructor


    /*
     * public methods
     */


    /**
     * Add a dealt card to our hand
     *
     * @param dealt
     *     the card we're dealt
     */
    public void dealtACard( final Card dealt )
        {

        this.hand.addToBottom( dealt ) ;
        this.hand.sort() ;

        }  // end dealtACard()


    /**
     * retrieve the number of points
     *
     * @return the number of points accumulated
     */
    public int getPoints()
        {

        return this.points ;

        }   // end getPoints()


    /**
     * Remove an unspecified card from our hand
     *
     * @return any card currently in the hand
     *
     * @throws NoCardsException
     *     if the hand is empty
     */
    public Card playACard()
            throws NoCardsException
        {

        return this.hand.removeCardAt( new Random().nextInt( 0,
                                                             this.hand.cardCount() ) ) ;

        }  // end playACard()


    /**
     * Remove a specified card from our hand
     *
     * @param cardToThrow
     *     the card to remove
     *
     * @return the specified card or null if not in the hand
     */
    public Card playACard( final Card cardToThrow )
        {

        return this.hand.removeCard( cardToThrow ) ;

        }  // end playACard()


    /**
     * Remove a specified card from our hand
     *
     * @param color
     *     the color of the card to remove
     * @param value
     *     the face value or action of the card to remove
     *
     * @return the specified card or null if not in the hand
     */
    public Card playACard( final CardColor color,
                           final CardValue value )
        {

        return playACard( newCardLike( color,
                                       value ) ) ;

        }  // end playACard()


    /**
     * text describing the contents of the player's hand
     * <p>
     * note that cards' orientation is unchanged
     *
     * @return a string containing the cards in the player's hand
     */
    public String revealHand()
        {

        if ( this.hand.cardCount() == 0 )
            {

            return "empty" ;

            }

        return this.hand.revealAll()
                        .toString() ;

        }   // end revealHand()


    /**
     * Remove all cards from our hand and our collected cards
     *
     * @return a pile with all the cards we have - order and orientation may be inconsistent
     */
    public Pile turnInAllCards()
        {

        // local temporary class (pile) to hold our cards
        final Pile allCards = newTransferPile() ;

        // we may be holding cards - collect them

        allCards.moveCardsToBottom( this.hand ) ;

        // assertion: we have no cards, any we had will be returned via allCards

        return allCards ;

        }  // end turnInAllCards()


    /**
     * record that we won a round
     *
     * @param cardsWon
     *     the cards this player won
     */
    public void wonRound( final Pile cardsWon )
        {

        this.roundsWon++ ;
        this.points++ ;

        }   // end cardsWon()


    /*
     * utility methods
     */


    @Override
    public String toString()
        {

        return String.format( "%nPlayer: %s%n\tpoints: %,d; rounds won: %,d%n\thand: %s",
                              this.name,
                              this.points,
                              this.roundsWon,
                              revealHand() ) ;

        }   // end toString()


    /*
     * testing/debugging
     */


    /**
     * (optional) test driver
     *
     * @param args
     *     -unused-
     */
    public static void main( final String[] args )
        {

        // we'll sort by value only
        Card.setCompareOnAttributes( CompareOn.COMPARE_VALUE_ONLY ) ;

        final Deck testDeck = new Deck() ;

        // create the stock initially populated with all the cards from the deck
        final Stock testStock = new Stock( testDeck ) ;

        // shuffle them
        testStock.shuffle() ;

        testStock.revealAll() ;
        System.out.printf( "Stock: %s%n%n",
                           testStock ) ;
        testStock.hideAll() ;

        testDeck.revealAll() ;
        System.out.printf( "Deck: %s%n%n",
                           testDeck ) ;
        testDeck.hideAll() ;


        final Player testPlayer = new Player( "tester" ) ;

        System.out.printf( "start: %s%n",
                           testPlayer ) ;

        for ( int i = 1 ; i <= 5 ; i++ )
            {

            final Card dealt = testStock.drawTopCard()
                                        .reveal() ;

            testPlayer.dealtACard( dealt ) ;

            }

        System.out.printf( "%ndealt: %s%n",
                           testPlayer ) ;

        for ( int i = 1 ; i <= 3 ; i++ )
            {

            final Pile someCards = newTransferPile().setDefaultFaceUp() ;

            for ( int j = 1 ; j <= 5 ; j++ )
                {

                someCards.addToTop( testStock.drawTopCard() ) ;

                }

            testPlayer.wonRound( someCards ) ;

            }

        System.out.printf( "%nwith some cards: %s%n",
                           testPlayer ) ;


        // the following is the correct way to access a file in the data folder
        System.out.printf( "%n%naccessing a file in the data folder:%n%n" ) ;

        try ( Scanner input = new Scanner( new File( "./data/readme.txt" ) ) ; )
            {

            while ( input.hasNextLine() )
                {

                System.out.printf( "%s%n",
                                   input.nextLine() ) ;

                }

            }
        catch ( final FileNotFoundException e )
            {

            System.err.printf( "failed to open readme.txt:%n%s%n",
                               e ) ;

            }

        }   // end main()

    }   // end class Player
