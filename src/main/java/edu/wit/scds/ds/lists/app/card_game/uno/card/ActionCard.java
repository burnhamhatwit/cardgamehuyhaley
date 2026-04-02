

// do not modify this code


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


package edu.wit.scds.ds.lists.app.card_game.uno.card ;

import edu.wit.scds.ds.lists.app.card_game.universal_base.card.CardBase ;
import edu.wit.scds.ds.lists.app.card_game.universal_base.support.Persistence ;
import edu.wit.scds.ds.lists.app.card_game.uno.card.CardValue.Kind ;

import static edu.wit.scds.ds.lists.app.card_game.universal_base.support.Orientation.FACE_UP ;

import java.util.ArrayList ;
import java.util.Arrays ;
import java.util.Collections ;
import java.util.List ;


/**
 * Representation of a playing card with a color and value
 * <p>
 * The color and value are immutable.
 * <p>
 * Note: we will override all superclass methods that our game uses which return a {@link CardBase} reference
 * to reduce/eliminate the need to cast to {@code Card}, particularly for fluent methods
 *
 * @author Dave Rosenberg
 *
 * @version 1.0 2025-11-11 Initial implementation based upon {@code standard_cards.card.Card}
 */
public final class ActionCard
        extends Card
    {


    /*
     * constructors
     */


    /**
     * Initialize a card with a specified color and value
     *
     * @param theValue
     *     this card's face value
     * @param theColor
     *     this card's color
     */
    public ActionCard( final CardColor theColor,
                       final CardValue theValue )
        {

        super( theColor,
               theValue ) ;

        // assertion: neither argument is null

        if ( theValue.getKind() != Kind.ACTION )
            {

            throw new IllegalArgumentException( String.format( "cannot create a(n) %s card with a{n} %s value",
                                                               Kind.ACTION,
                                                               theValue.getKind() ) ) ;

            }

        }   // end 2-arg constructor


    /**
     * create a temporary clone of a card, typically for searching
     *
     * @param sourceCard
     *     the card to copy
     */
    public ActionCard( final ActionCard sourceCard )
        {

        super( sourceCard ) ;

        // assertion: the sourceCard is not null

        }   // end 1-arg 'cloning' constructor


    /*
     * for testing/debugging
     */


    /**
     * Sample demo program
     *
     * @param args
     *     -unused-
     */
    public static void main( final String[] args )
        {

        final CardColor[] colors = CardColor.values() ;
        final CardValue[] values = CardValue.values() ;

        final List<Card> cards = new ArrayList<>( colors.length * values.length * 4 ) ;
        // rough estimate

        System.out.printf( "Key: colors are: %s; values are: %s%n%n",
                           Arrays.toString( colors ),
                           Arrays.toString( values ) ) ;

        // generate a deck of cards
        System.out.printf( "New cards:%n" ) ;

        // make them permanent
        setDefaultPersistence( Persistence.PERMANENT ) ;

        for ( final CardValue value : values )
            {

            // only create action cards
            if ( value.getKind() != Kind.ACTION )
                {

                continue ;

                }

            // build the necessary number of cards for each color
            for ( final CardColor color : value.getColors() )
                {

                for ( int i = 1 ; i <= value.getQuantity() ; i++ )
                    {

                    final Card newCard = new ActionCard( color,
                                                         value ) ;
                    System.out.printf( " %s",
                                       newCard ) ;

                    // keep track of it
                    cards.add( newCard ) ;

                    }

                }

            }

        // reset card permanence
        resetDefaultPersistence() ;

        // turn top card over
        cards.getFirst()
             .flip() ;

        // display all the cards
        System.out.printf( "%n%nAll cards:%n%s%n%n",
                           cards.toString() ) ;

        // turn all cards face up
        for ( final Card aCard : cards )
            {

            aCard.reveal() ;

            }

        // display all the cards
        System.out.printf( "%n%nAll cards:%n%s%n%n",
                           cards.toString() ) ;

        // shuffled
        Collections.shuffle( cards ) ;
        System.out.printf( "%nShuffled:%n%s%n%n",
                           cards.toString() ) ;

        // sorted
        Collections.sort( cards ) ;
        System.out.printf( "%nSorted (value and color):%n%s%n%n",
                           cards.toString() ) ;

        // sort only on value
        setCompareOnAttributes( CompareOn.COMPARE_VALUE_ONLY ) ;

        // shuffled
        Collections.shuffle( cards ) ;
        System.out.printf( "%nShuffled:%n%s%n%n",
                           cards.toString() ) ;

        // sorted
        Collections.sort( cards ) ;
        System.out.printf( "%nSorted (value only):%n%s%n%n",
                           cards.toString() ) ;

        // sorted
        Collections.sort( cards ) ;
        System.out.printf( "%nSorted (value only):%n%s%n%n",
                           cards.toString() ) ;

        // sort on value and color
        setCompareOnAttributes( CompareOn.COMPARE_COLOR_AND_VALUE ) ;

        // sorted
        Collections.sort( cards ) ;
        System.out.printf( "%nSorted (value and color):%n%s%n%n",
                           cards.toString() ) ;


        // compare some cards against each other
        Card card1 = cards.get( 2 ) ;
        Card card2 = cards.get( 3 ) ;
        System.out.printf( "%s.compareTo(%s) = %+,d (value and color)%n",
                           card1,
                           card2,
                           card1.compareTo( card2 ) ) ;
        System.out.printf( "%s.compareTo(%s) = %+,d (value and color)%n",
                           card2,
                           card1,
                           card2.compareTo( card1 ) ) ;

        card1 = cards.get( 15 ) ;
        card2 = cards.get( 19 ) ;
        System.out.printf( "%s.compareTo(%s) = %+,d (value and color)%n",
                           card1,
                           card2,
                           card1.compareTo( card2 ) ) ;


        card2 = cards.get( 4 ) ;
        System.out.printf( "%s.compareTo(%s) = %+,d (value and color)%n",
                           card1,
                           card2,
                           card1.compareTo( card2 ) ) ;

        card2 = cards.get( 18 ) ;
        System.out.printf( "%s.compareTo(%s) = %+,d (value and color)%n",
                           card1,
                           card2,
                           card1.compareTo( card2 ) ) ;


        System.out.printf( "%n" ) ;
        card1 = cards.get( 2 ) ;
        card2 = cards.get( 3 ) ;
        System.out.printf( "%s.equals(%s) = %b (value and color)%n",
                           card1,
                           card2,
                           card1.equals( card2 ) ) ;
        System.out.printf( "%s.equals(%s) = %b (value and color)%n",
                           card1,
                           card1,
                           card1.equals( card1 ) ) ;
        System.out.printf( "%s.equals(%s) = %b (value and color)%n",
                           card2,
                           card1,
                           card2.equals( card1 ) ) ;
        System.out.printf( "%s == %s = %b (value and color)%n",
                           card1,
                           card2,
                           card1 == card2 ) ;

        System.out.printf( "%ncreating temporary cards%n" ) ;
        CardBase.setDefaultOrientation( FACE_UP ) ;
        card1 = new ActionCard( CardColor.BLUE,
                                CardValue.SKIP ) ;
        card2 = new ActionCard( CardColor.YELLOW,
                                CardValue.REVERSE ) ;
        System.out.printf( "%s.equals(%s) = %b (value and color)%n",
                           card1,
                           card2,
                           card1.equals( card2 ) ) ;


        System.out.printf( "%n" ) ;
        System.out.printf( "%s.matches(%s) = %b (value and color)%n",
                           card1,
                           card1,
                           card1.matches( card1 ) ) ;
        System.out.printf( "%s.matches(%s) = %b (value and color)%n",
                           card1,
                           card2,
                           card1.matches( card2 ) ) ;
        System.out.printf( "%s == %s = %b (value and color)%n",
                           card1,
                           card2,
                           card1 == card2 ) ;

        CardBase.setDefaultOrientation( FACE_UP ) ;
        card1 = new ActionCard( CardColor.GREEN,
                                CardValue.WILD ) ;
        card2 = new ActionCard( CardColor.BLUE,
                                CardValue.WILD ) ;
        System.out.printf( "%s.matches(%s) = %b (value and color)%n",
                           card1,
                           card2,
                           card1.matches( card2 ) ) ;


        // repeat comparisons without considering color
        setCompareOnAttributes( CompareOn.COMPARE_VALUE_ONLY ) ;

        System.out.printf( "%n" ) ;

        // compare some cards against each other
        card1 = cards.get( 15 ) ;
        card2 = cards.get( 18 ) ;
        System.out.printf( "%s.compareTo(%s) = %+,d (value only)%n",
                           card1,
                           card2,
                           card1.compareTo( card2 ) ) ;

        card2 = cards.get( 4 ) ;
        System.out.printf( "%s.compareTo(%s) = %+,d (value only)%n",
                           card1,
                           card2,
                           card1.compareTo( card2 ) ) ;

        card2 = cards.get( 16 ) ;
        System.out.printf( "%s.compareTo(%s) = %+,d (value only)%n",
                           card1,
                           card2,
                           card1.compareTo( card2 ) ) ;


        System.out.printf( "%n" ) ;
        System.out.printf( "%s.equals(%s) = %b (value only)%n",
                           card1,
                           card1,
                           card1.equals( card1 ) ) ;
        System.out.printf( "%s.equals(%s) = %b (value only)%n",
                           card1,
                           card2,
                           card1.equals( card2 ) ) ;


        setCompareOnAttributes( CompareOn.COMPARE_COLOR_AND_VALUE ) ;

        CardBase.setDefaultOrientation( FACE_UP ) ;
        card1 = new ActionCard( CardColor.GREEN,
                                CardValue.DRAW_2 ) ;
        card2 = new ActionCard( CardColor.BLUE,
                                CardValue.DRAW_2 ) ;
        System.out.printf( "%s.equals(%s) = %b (value and color)%n",
                           card1,
                           card2,
                           card1.equals( card2 ) ) ;


        setCompareOnAttributes( CompareOn.COMPARE_COLOR_ONLY ) ;

        System.out.printf( "%s.equals(%s) = %b (color only)%n",
                           card1,
                           card2,
                           card1.equals( card2 ) ) ;


        setCompareOnAttributes( CompareOn.COMPARE_VALUE_ONLY ) ;

        System.out.printf( "%s.equals(%s) = %b (value only)%n",
                           card1,
                           card2,
                           card1.equals( card2 ) ) ;


        CardBase.setDefaultOrientation( FACE_UP ) ;
        card1 = new ActionCard( CardColor.GREEN,
                                CardValue.WILD_DRAW_4 ) ;
        card2 = new ActionCard( CardColor.BLUE,
                                CardValue.WILD_DRAW_4 ) ;
        System.out.printf( "%s.equals(%s) = %b (value and color)%n",
                           card1,
                           card2,
                           card1.equals( card2 ) ) ;

        setCompareOnAttributes( CompareOn.COMPARE_VALUE_ONLY ) ;
        System.out.printf( "%s.equals(%s) = %b (value only)%n",
                           card2,
                           card1,
                           card2.equals( card1 ) ) ;


        System.out.printf( "%s.equals(%s) = %b (value and color)%n",
                           card1,
                           card2,
                           card1.equals( card2 ) ) ;
        System.out.printf( "%s.equals(%s) = %b (value and color)%n",
                           card2,
                           card1,
                           card2.equals( card1 ) ) ;


        setCompareOnAttributes( CompareOn.COMPARE_VALUE_ONLY ) ;

        System.out.printf( "%n" ) ;
        System.out.printf( "%s.matches(%s) = %b (value only)%n",
                           card1,
                           card1,
                           card1.matches( card1 ) ) ;
        System.out.printf( "%s.matches(%s) = %b (value only)%n",
                           card1,
                           card2,
                           card1.matches( card2 ) ) ;

        CardBase.setDefaultOrientation( FACE_UP ) ;
        card1 = new ActionCard( CardColor.GREEN,
                                CardValue.SKIP ) ;
        card2 = new ActionCard( CardColor.BLUE,
                                CardValue.SKIP ) ;
        System.out.printf( "%s.matches(%s) = %b (value only)%n",
                           card1,
                           card2,
                           card1.matches( card2 ) ) ;

        card1 = new ActionCard( CardColor.ANY,
                                CardValue.REVERSE ) ;
        card2 = new ActionCard( CardColor.BLUE,
                                CardValue.REVERSE ) ;
        System.out.printf( "%s.matches(%s) = %b%n",
                           card1,
                           card2,
                           card1.matches( card2 ) ) ;

        }   // end main()

    }   // end class ActionCard
