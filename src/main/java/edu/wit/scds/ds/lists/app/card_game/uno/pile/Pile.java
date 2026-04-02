
// you probably will not modify this code - talk to me if you have any questions


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
import edu.wit.scds.ds.lists.app.card_game.universal_base.pile.PileBase ;
import edu.wit.scds.ds.lists.app.card_game.universal_base.support.Orientation ;
import edu.wit.scds.ds.lists.app.card_game.uno.card.Card ;
import edu.wit.scds.ds.lists.app.card_game.uno.card.CardColor ;
import edu.wit.scds.ds.lists.app.card_game.uno.card.CardValue ;

import static edu.wit.scds.ds.lists.app.card_game.universal_base.support.Orientation.FACE_DOWN ;
import static edu.wit.scds.ds.lists.app.card_game.uno.card.Card.newCardLike ;

import java.util.Iterator ;
import java.util.ListIterator ;


/**
 * Representation of a pile of Uno playing cards
 * <p>
 * the top card is at position 0
 *
 * @author Dave Rosenberg
 *
 * @version 1.0 2025-11-11 Initial implementation based upon {@code standard_cards.pile.Pile}
 * @version 1.1 2025-11-27
 *     <ul>
 *     <li>fully support iteration
 *     <li>extend functionality to support deck validation
 *     </ul>
 * @version 1.2 2026-03-26 add factory for a temporary, limited-purpose collection of {@code Card}s to
 *     facilitate transferring them
 */
public abstract class Pile
        extends PileBase
    {

    /*
     * utility constants
     */


    /** by default, cards added to this pile will be turned face down */
    private final static Orientation DEFAULT_CARD_ORIENTATION = FACE_DOWN ;


    /*
     * data fields
     */
    // none


    /*
     * constructors
     */


    /**
     * Initialize the pile with cards placed face down by default
     */
    protected Pile()
        {

        this( DEFAULT_CARD_ORIENTATION ) ;

        }   // end no-arg constructor


    /**
     * Initialize the pile with cards placed face up/down as specified by default
     *
     * @param initialOrientation
     *     specify whether cards will be face up or down by default
     */
    protected Pile( final Orientation initialOrientation )
        {

        super( initialOrientation ) ;

        }   // end 1-arg constructor
    
    
    /*
     * factory methods
     */
    

    /**
     * provides a minimal pile to enable transfer of multiple cards
     * 
     * @return a limited-purpose pile
     */
    public static Pile newTransferPile()
        {

        return new TransferPile() ;

        }   // end newTransferPile()


    /*
     * public methods
     */


    @Override
    public Card getCardLike( final CardBase likeCard )
        {

        return (Card) super.getCardLike( likeCard ) ;

        }  // end getCardLike() given a Card


    /**
     * Retrieve a specific card from the pile by card - the card is not removed from the pile
     *
     * @param color
     *     the color of the desired card
     * @param value
     *     the value of the desired card
     *
     * @return the specified card or {@code null} if the card isn't in the pile
     */
    public Card getCardLike( final CardColor color,
                             final CardValue value )
        {

        return getCardLike( newCardLike( color,
                                         value ) ) ;

        }  // end getCardLike() given color and value


    @Override
    public Iterator<CardBase> iterator()
        {

        return new CardIterator() ;

        }   // end iterator()


    /**
     * creates a list iterator for this pile
     *
     * @return a new {@code ListIterator}
     */
    @Override
    public ListIterator<CardBase> listIterator()
        {

        return new CardIterator() ;

        }   // end listIterator()


    /**
     * Remove all cards from the pile
     *
     * @return a new pile containing the removed cards, if any
     */
    public Pile removeAllCards()
        {

        final Pile allCards = newTransferPile() ;

        for ( final CardBase card : super.removeAll() )
            {

            allCards.addToBottom( card ) ;

            }

        return allCards ;

        }  // end removeAllCards()


    /**
     * Remove all instances of a specific card from the pile
     *
     * @param lookupCard
     *     the card to be removed
     *
     * @return a new pile containing the removed cards, if any
     */
    public Pile removeAllMatchingCards( final Card lookupCard )
        {

        final Pile removedCards = newTransferPile() ;

        super.removeAllMatchingCards( lookupCard,
                                      removedCards ) ;

        return removedCards ;

        }  // end removeAllMatchingCards()


    @Override
    public Card removeCard( final CardBase card )
        {

        return (Card) super.removeCard( card ) ;

        }  // end removeCard() given a card


    /**
     * Remove a specific card from the pile by card
     *
     * @param color
     *     color of the card to be removed
     * @param value
     *     value of the card to be removed
     *
     * @return the specified card or {@code null} if the card isn't in the pile
     */
    public Card removeCard( final CardColor color,
                            final CardValue value )
        {

        return removeCard( newCardLike( color,
                                        value ) ) ;

        }  // end removeCard() given a color and value


    @Override
    public Card removeCardAt( final int position )
        {

        return (Card) super.removeCardAt( position ) ;

        }  // end removeCardAt()


    @Override
    public Card removeTopCard()
        {

        return (Card) super.removeTopCard() ;

        }  // end removeTopCard()


    @Override
    public Pile setDefaultFaceDown()
        {

        super.setDefaultFaceDown() ;

        return this ;

        }   // end setFaceDown()


    @Override
    public Pile setDefaultFaceUp()
        {

        super.setDefaultFaceUp() ;

        return this ;

        }   // end setFaceUp()


    /*
     * utility classes
     */
    

    /**
     * temporary pile for transferring cards between piles
     */
    private static class TransferPile
            extends Pile
        {}


    /**
     * enable iteration over all cards in the pile
     */
    private class CardIterator
            implements ListIterator<CardBase>
        {

        /** the actual iterator is that of the PileBase */
        private final ListIterator<CardBase> cardIterator ;


        /**
         * configure the instance state
         */
        private CardIterator()
            {

            this.cardIterator = Pile.super.listIterator() ;

            }   // end constructor


        @Override
        public boolean hasNext()
            {

            return this.cardIterator.hasNext() ;

            }   // end hasNext()


        @Override
        public Card next()
            {

            return (Card) this.cardIterator.next() ;

            }   // end next()


        @Override
        public boolean hasPrevious()
            {

            return this.cardIterator.hasPrevious() ;

            }   // end hasPrevious()


        @Override
        public Card previous()
            {

            return (Card) this.cardIterator.previous() ;

            }   // end previous()


        @Override
        public int nextIndex()
            {

            return this.cardIterator.nextIndex() ;

            }   // end nextIndex()


        @Override
        public int previousIndex()
            {

            return this.cardIterator.previousIndex() ;

            }   // end previousIndex()


        @Override
        public void remove()
            {

            this.cardIterator.remove() ;

            }   // end remove()


        @Override
        public void set( final CardBase replacementCard )
            {

            this.cardIterator.set( replacementCard ) ;

            }   // end set()


        @Override
        public void add( final CardBase newCard )
            {

            this.cardIterator.add( newCard ) ;

            }   // end add()

        }   // end inner class CardIterator

    }   // end class Pile
