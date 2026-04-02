

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


package edu.wit.scds.ds.lists.app.card_game.uno.pile ;

import edu.wit.scds.ds.lists.app.card_game.universal_base.card.CardBase ;
import edu.wit.scds.ds.lists.app.card_game.universal_base.support.Orientation ;
import edu.wit.scds.ds.lists.app.card_game.universal_base.support.Persistence ;
import edu.wit.scds.ds.lists.app.card_game.uno.card.Card ;
import edu.wit.scds.ds.lists.app.card_game.uno.card.CardColor ;
import edu.wit.scds.ds.lists.app.card_game.uno.card.CardValue ;

import static edu.wit.scds.ds.lists.app.card_game.universal_base.support.Orientation.FACE_DOWN ;
import static edu.wit.scds.ds.lists.app.card_game.uno.card.Card.newCardLike ;


/**
 * Representation of a deck of Uno cards containing
 * <ul>
 * <li>4 colors: Red, Yellow, Green, Blue
 * <li>10 numeric values: 0..9, inclusive
 * <li>5 actions: skip, reverse, draw 2, wild, wild draw 4
 * </ul>
 * as follows
 * <ul>
 * <li>1 each: 0 of each color
 * <li>2 each: 1..9 of each color
 * <li>2 each: skip, reverse, draw 2 of each color
 * <li>4 each: wild, wild draw 4 of any color (changeable during game play to any color)
 * </ul>
 * <p>
 * this is the source of all cards available to the game
 *
 * @author Dave Rosenberg
 *
 * @version 1.0 2025-11-11 Initial implementation based upon {@code standard_cards.pile.Deck}
 * @version 1.1 2025-11-25
 *     <ul>
 *     <li>add support for a template collection of {@code Card}s
 *     <li>remove options to modify contents of deck on instantiation - a deck is a deck
 *     </ul>
 */
public final class Deck
        extends Pile
    {

    /*
     * utility constants
     */


    /** by default, cards added to this deck will be turned face down */
    protected final static Orientation DEFAULT_CARD_ORIENTATION = FACE_DOWN ;


    /*
     * static fields
     */


    /**
     * template deck contains all playing cards and the default number of jokers
     */
    private final static Deck templateCards ;

    /** number of cards in a deck (as deal) */
    public final static int CARD_COUNT_IN_A_DECK ;

    static
        {

        // instantiate a template deck
        templateCards = new Deck( Persistence.TEMPLATE ) ;

        CARD_COUNT_IN_A_DECK = templateCards.cardCount() ;

        }   // end static initializer


    /*
     * data fields
     */
    // none


    /*
     * constructors
     */


    /**
     * Initialize a deck of cards<br>
     * the cards are in the order as specified by {@link #createTemplateCards()}
     */
    public Deck()
        {

        super() ;

        createPlayingCards() ;

        }   // end no-arg constructor


    /**
     * Initialize a deck of template cards<br>
     * the cards are in the order as specified by {@link #createTemplateCards()}
     * <p>
     * This constructor provides a reliable mechanism to instantiate the template deck
     *
     * @param persistence
     *     usually {@code Persistence.TEMPLATE}
     */
    private Deck( final Persistence persistence )
        {

        // initialize the pile
        super() ;

        // persistence must be template
        if ( persistence != Persistence.TEMPLATE )
            {

            throw new IllegalStateException( String.format( "can't create template deck with %s cards",
                                                            persistence ) ) ;

            }

        // populate it as a deck
        createTemplateCards() ;

        }   // end private template constructor


    /*
     * public methods
     */


    /**
     * return the number of cards in this deck
     *
     * @return the number of cards in the template
     */
    public int size()
        {

        return cardCount() ;

        }   // end size()


    /*
     * private utility methods
     */


    /**
     * Instantiate all the playing cards in a new deck
     * <ul>
     * <li>cards are generated by color within value
     * </ul>
     */
    private void createTemplateCards()
        {

        final CardValue[] values = CardValue.values() ;

        // generate all the cards in the deck

        final Persistence savedPersistence = CardBase.setDefaultPersistence( Persistence.TEMPLATE ) ;

        // {@code CardValue} specifies the number of cards for each color needed for each value
        for ( final CardValue value : values )
            {

            final int quantity = value.getQuantity() ;

            // build the necessary number of cards for each color
            for ( final CardColor color : value.getColors() )
                {

                for ( int i = 1 ; i <= quantity ; i++ )
                    {

                    // build a card and save it
                    final Card newCard = newCardLike( color,
                                                      value ) ;

                    super.cards.add( newCard ) ;

                    } // end for: quantities

                } // end for: colors

            } // end for: values

        CardBase.setDefaultPersistence( savedPersistence ) ;

        }  // end createTemplateCards()


    /**
     * build a deck of cards using the template cards
     */
    private void createPlayingCards()
        {

        final Persistence savedPersistence = CardBase.setDefaultPersistence( Persistence.PERMANENT ) ;

        // create one permanent card for each template card
        for ( final CardBase aCardBase : Deck.templateCards )
            {

            if ( ! ( aCardBase instanceof final Card aCard ) )
                {

                throw new IllegalStateException( String.format( "encountered unexpected %s card among template cards",
                                                                aCardBase.getClass()
                                                                         .getSimpleName() ) ) ;

                }

            final Card newCard = newCardLike( aCard ) ;
            addToBottom( newCard ) ;

            }

        CardBase.setDefaultPersistence( savedPersistence ) ;

        }   // end createPlayingCards()


    /**
     * ensure that this deck contains the all the cards it had when instantiated and nothing else
     *
     * @throws IllegalStateException
     *     if any cards are missing or contains any stray cards
     */
    public void validateDeck()
            throws IllegalStateException
        {

        // utility piles of missing cards and remaining cards
        final Pile missingCards = new Pile()
            {} ;
        missingCards.setDefaultOrientation( Orientation.AS_IS )
                    .setAcceptablePersistence( Persistence.UNRESTRICTED ) ;


        // move all cards from this deck to a temporary collection
        final Pile temporaryDeck = removeAllCards() ;

        // iterate over the template moving corresponding cards from the temporary collection back into this
        // deck
        for ( final CardBase templateCard : Deck.templateCards )
            {

            final CardBase realCard = temporaryDeck.removeCard( templateCard ) ;

            // if a card is missing, capture it otherwise put it back in our list
            if ( realCard == null )
                {

                missingCards.addToBottom( templateCard ) ;

                }
            else
                {

                addToBottom( realCard ) ;

                }

            }

        // if there are any collected problems, report them via exception
        if ( ( missingCards.cardCount() != 0 ) || ( temporaryDeck.cardCount() != 0 ) )
            {

            missingCards.revealAll() ;
            temporaryDeck.revealAll() ;

            // failed validation - report it
            throw new IllegalStateException( String.format( "Deck validation failed; missing: %s; strays: %s",
                                                            missingCards.cardCount() == 0
                                                                    ? "none"
                                                                    : missingCards,
                                                            temporaryDeck.cardCount() == 0
                                                                    ? "none"
                                                                    : temporaryDeck ) ) ;

            }

        }   // end validateDeck()


    /*
     * for testing/debugging
     */


    /**
     * (optional) test driver
     *
     * @param args
     *     -unused-
     */
    public static void main( final String[] args )
        {

        final Orientation savedOrientation = CardBase.setDefaultOrientation( Orientation.FACE_UP ) ;

        Deck.templateCards.revealAll() ;
        System.out.printf( "template:%n%s%n%n",
                           Deck.templateCards ) ;
        Deck.templateCards.hideAll() ;

        final Deck testDeck = new Deck() ;
        testDeck.setDefaultOrientation( Orientation.AS_IS ) ;
        System.out.printf( "testDeck:%n%s%n%n",
                           testDeck ) ;
        testDeck.revealAll() ;
        System.out.printf( "testDeck:%n%s%n%n",
                           testDeck ) ;
        testDeck.setDefaultOrientation( Orientation.FACE_UP ) ;

        final Card testCard = newCardLike( CardColor.BLUE,
                                           CardValue.SEVEN ).reveal() ;
        System.out.printf( "testCard: %s%n",
                           testCard ) ;

        try
            {

            System.out.printf( "add the %s to the deck (won't end well!)%n",
                               testCard ) ;
            testDeck.addToTop( testCard ) ; // should fail
            testDeck.validateDeck() ;
            System.out.printf( "...we shouldn't be ok%n" ) ;

            }
        catch ( final IllegalArgumentException e )
            {

            System.out.printf( "blew up: %s%n",
                               e.getMessage() ) ;

            }

        System.out.printf( "validating deck...%n" ) ;
        testDeck.validateDeck() ;
        System.out.printf( "...ok%n" ) ;


        Card removedCard = null ;

        try
            {

            removedCard = testDeck.removeTopCard()
                                  .reveal() ;

            System.out.printf( "%nmessing up deck... removed %s...%n",
                               removedCard ) ;
            System.out.printf( "revalidating deck...%n" ) ;
            testDeck.validateDeck() ;   // should fail
            System.out.printf( "...we shouldn't be ok%n" ) ;

            }
        catch ( final IllegalStateException e )
            {

            System.out.printf( "blew up: %s%n",
                               e.getMessage() ) ;

            }

        if ( removedCard != null )
            {

            System.out.printf( "%nrestoring deck... adding %s...%n",
                               removedCard ) ;
            // put the card back on top of the deck
            testDeck.addToTop( removedCard ) ;

            }

        System.out.printf( "%nrevalidating deck...%n" ) ;
        testDeck.validateDeck() ;
        System.out.printf( "...ok%n" ) ;

        try
            {

            final Persistence savedPersistence = CardBase.setDefaultPersistence( Persistence.PERMANENT ) ;
            final Card extraCard = newCardLike( CardColor.BLUE,
                                                CardValue.FOUR ).reveal() ;
            CardBase.setDefaultPersistence( savedPersistence ) ;

            System.out.printf( "%nmessing up deck... adding %s...%n",
                               extraCard ) ;
            testDeck.addAtPosition( extraCard,
                                    7 ) ;
            System.out.printf( "revalidating deck...%n" ) ;
            testDeck.validateDeck() ;   // should fail
            System.out.printf( "...we shouldn't be ok%n" ) ;

            }
        catch ( final IllegalStateException e )
            {

            System.out.printf( "blew up: %s%n",
                               e.getMessage() ) ;

            }

        // the extra card won't be added back into the test deck

        System.out.printf( "%nrevalidating deck...%n" ) ;
        testDeck.validateDeck() ;   // should succeed
        System.out.printf( "...ok%n" ) ;

        try
            {

            final Persistence savedPersistence = CardBase.setDefaultPersistence( Persistence.PERMANENT ) ;
            final Card extraCard = newCardLike( removedCard ).reveal() ;
            CardBase.setDefaultPersistence( savedPersistence ) ;

            testDeck.addAtPosition( extraCard,
                                    7 ) ;
            removedCard = testDeck.removeCardAt( 11 ) ;

            System.out.printf( "%nmessing up deck... adding %s, removing %s...%n",
                               extraCard,
                               removedCard ) ;
            System.out.printf( "revalidating deck...%n" ) ;
            testDeck.validateDeck() ;   // should fail
            System.out.printf( "...we shouldn't be ok%n" ) ;

            }
        catch ( final IllegalStateException e )
            {

            System.out.printf( "blew up: %s%n",
                               e.getMessage() ) ;

            }

        // the extra card won't be added back into the test deck

        System.out.printf( "putting %s back in the deck%n",
                           removedCard ) ;
        testDeck.addToTop( removedCard ) ;

        System.out.printf( "%nrevalidating deck...%n" ) ;
        testDeck.validateDeck() ;   // should succeed
        System.out.printf( "...ok%n" ) ;

        System.out.printf( "%ndone.%n" ) ;

        CardBase.setDefaultOrientation( savedOrientation ) ;

        }   // end main()

    }   // end class Deck
