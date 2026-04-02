

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

import static edu.wit.scds.ds.lists.app.card_game.universal_base.support.Orientation.FACE_DOWN ;
import static edu.wit.scds.ds.lists.app.card_game.universal_base.support.Orientation.FACE_UP ;
import static edu.wit.scds.ds.lists.app.card_game.universal_base.support.Persistence.TEMPORARY ;

import java.util.Objects ;


/**
 * Representation of an Uno playing card with a color and values
 * <p>
 * The color and value are immutable.
 * <p>
 * Note: we will override all superclass methods that our game uses which return a {@link CardBase} reference
 * to reduce/eliminate the need to cast to {@code Card}, particularly for fluent methods
 *
 * @author Dave Rosenberg
 *
 * @version 1.0 2025-11-11 Initial implementation based upon {@code standard_cards.card.Card}
 * @version 1.1 2025-11-27 add support for template cards
 */
public abstract class Card
        extends CardBase
    {

    /*
     * utility constants
     */


    /**
     * specifiers for which attributes of a {@code Card} should be used when comparing instances
     */
    public enum CompareOn
        {

        // @formatter:off

           /** for comparison operations: equals, matches, compareTo: only consider color */
         COMPARE_COLOR_ONLY

         , /** for comparison operations: equals, matches, compareTo: only consider value */
         COMPARE_VALUE_ONLY

         , /** for comparison operations: equals, matches, compareTo: consider both color and value */
         COMPARE_COLOR_AND_VALUE

         , /** effectively disables entity comparison */
         COMPARE_NONE

         ;

        // @formatter:on

        }   // end enum CompareOn


    /*
     * static data
     */


    /**
     * controls the selection of attributes to use in {@code Card} comparisons
     */
    private static CompareOn compareOnAttributes = CompareOn.COMPARE_COLOR_AND_VALUE ;


    /*
     * data fields
     */


    /** The card's color */
    public final CardColor color ;

    /** The card's value within its color */
    public final CardValue value ;


    /*
     * constructors
     */


    /**
     * Initialize a card with a specified color and value
     *
     * @param theColor
     *     this card's color
     * @param theValue
     *     this card's face value
     */
    public Card( final CardColor theColor,
                 final CardValue theValue )
        {

        Objects.requireNonNull( theColor,
                                "theColor" ) ;
        Objects.requireNonNull( theValue,
                                "theValue" ) ;

        this.color = theColor ;
        this.value = theValue ;

        setFaceUpText( String.format( "%s %s",
                                      this.color,
                                      this.value ) ) ;

        }   // end 2-arg constructor


    /**
     * create a temporary clone of a card, typically for searching
     *
     * @param sourceCard
     *     the card to copy
     */
    public Card( final Card sourceCard )
        {

        this( sourceCard,
              TEMPORARY ) ;

        }   // end 1-arg 'cloning' constructor


    /**
     * create a clone of a card, typically for searching
     *
     * @param sourceCard
     *     the card to copy
     * @param cardPersistence
     *     the persistence for the card
     */
    public Card( final Card sourceCard,
                 final Persistence cardPersistence )
        {

        super( sourceCard,
               cardPersistence ) ;

        // assertion: we have a source card

        this.color = sourceCard.color ;
        this.value = sourceCard.value ;

        super.setOrientation( sourceCard.orientation ) ;

        }   // end 2-arg 'cloning' constructor


    /*
     * getters and setters
     */
    // none - unnecessary


    /*
     * methods to affect card comparison behavior
     */


    /**
     * Retrieves the current behavior of {@code Card} comparisons
     *
     * @return the current setting
     */
    public static CompareOn getCompareOnAttributes()
        {

        return Card.compareOnAttributes ;

        }  // end getCompareOnAttributes()


    /**
     * Sets the behavior of {@code Card} comparisons
     *
     * @param newCompareOnAttributes
     *     the new evaluation behavior wrt card comparisons
     *
     * @return the previous state
     */
    public static CompareOn setCompareOnAttributes( final CompareOn newCompareOnAttributes )
        {

        Objects.requireNonNull( newCompareOnAttributes,
                                "newCompareOnAttributes" ) ;

        final CompareOn wasCompareOnAttributes = Card.compareOnAttributes ;

        Card.compareOnAttributes = newCompareOnAttributes ;

        return wasCompareOnAttributes ;

        }  // end setCompareOnAttributes()


    /*
     * overridden superclass methods - make it easy for these methods to be fluent
     */


    /*
     * methods to affect face up/down state and display of an instance
     */


    @Override
    public Card setFaceDown()
        {

        setOrientation( FACE_DOWN ) ;

        return this ;

        }   // end setFaceDown()


    @Override
    public Card setFaceUp()
        {

        setOrientation( FACE_UP ) ;

        return this ;

        }   // end setFaceUp()


    @Override
    public Card flip()
        {

        return (Card) super.flip() ;

        }   // end flip()


    @Override
    public Card hide()
        {

        return (Card) super.hide() ;

        }   // end hide()


    @Override
    public Card reveal()
        {

        return (Card) super.reveal() ;

        }   // end reveal()


    /*
     * general methods
     */


    @Override
    public int compareTo( final CardBase otherCard )
        {

        // make sure comparisons are permitted
        if ( compareOnNone() )
            {

            throw new UnsupportedOperationException( "comparisons are disabled" ) ;

            }

        // if other card is an Uno card, then compare value colors aren't meaningfully ordered so put them in
        // defined order

        if ( otherCard instanceof final Card otherUnoCard )
            {
            // other card is one of ours

            int cardComparison = 0 ;

            // assertion: comparison value will be (re)set by either or both of the tests

            // check value, if necessary
            if ( compareOnValue() )
                {

                cardComparison = this.value.getSortOrder() - otherUnoCard.value.getSortOrder() ;

                }

            // check color, if necessary
            if ( ( cardComparison == 0 ) && compareOnColor() )
                {

                cardComparison = this.color.ordinal() - otherUnoCard.color.ordinal() ;

                }

            return cardComparison ;

            }

        // other card is not one of ours or is null

        throw new IllegalArgumentException( String.format( "other card must be a %s but is a %s",
                                                           this.getClass()
                                                               .getSimpleName(),
                                                           otherCard == null
                                                                   ? null
                                                                   : otherCard.getClass()
                                                                              .getSimpleName() ) ) ;

        }   // end compareTo()


    @Override
    public boolean equals( final Object otherObject )
        {

        // make sure comparisons are permitted
        if ( Card.compareOnNone() )
            {

            throw new UnsupportedOperationException( "comparisons are disabled" ) ;

            }

        // same object?
        if ( this == otherObject )
            {

            return true ;

            }

        // another Uno card? false if otherObject is null
        if ( otherObject instanceof final Card otherCard )
            {

            return compareTo( otherCard ) == 0 ;

            }

        // not one of ours so can't match
        return false ;

        }   // end equals()


    @Override
    public int hashCode()
        {

        // make sure comparisons are permitted
        if ( Card.compareOnNone() )
            {

            throw new UnsupportedOperationException( "comparisons are disabled" ) ;

            }

        // assertion: value and/or color will be included

        return Objects.hash( compareOnValue()
                ? this.value
                : 0,
                             compareOnColor()
                                     ? this.color
                                     : 0 ) ;

        }   // end hashCode()


    /**
     * {@inheritDoc}
     * <p>
     * prevents comparison if disabled
     */
    @Override
    public boolean matches( final CardBase otherBaseCard )
        {

        // make sure comparisons are permitted
        if ( compareOnNone() )
            {

            throw new UnsupportedOperationException( "comparisons are disabled" ) ;

            }

        // same object?
        if ( this == otherBaseCard )
            {

            return true ;

            }

        // another Uno card? false if otherBaseCard is null
        if ( otherBaseCard instanceof final Card otherCard )
            {

            // delegate to the component version
            return this.matches( otherCard.value,
                                 otherCard.color ) ;

            }

        // not one of ours so can't match
        return false ;

        }   // end by-card matches()


    /**
     * determine if this card matches the specified components
     *
     * @param matchToValue
     *     the value to compare against ours if specified (non-{@code null})
     * @param matchToColor
     *     the color to compare against ours if specified (non-{@code null})
     *
     * @return {@code true} if either or both attributes match; false if neither matches
     */
    public boolean matches( final CardValue matchToValue,
                            final CardColor matchToColor )
        {

        // make sure comparisons are permitted
        if ( compareOnNone() )
            {

            throw new UnsupportedOperationException( "comparisons are disabled" ) ;

            }

        // match if value or color are the same
        return ( compareOnValue() && ( this.value == matchToValue ) )
               || ( compareOnColor() && ( this.color == matchToColor ) ) ;

        }   // end by-components matches()


    /**
     * create a new card resembling the provided card
     *
     * @param sourceCard
     *     card to mimic
     *
     * @return a new card resembling the source card
     */
    public static Card newCardLike( final Card sourceCard )
        {

        Objects.requireNonNull( sourceCard,
                                "sourceCard" ) ;

        return newCardLike( sourceCard.color,
                            sourceCard.value ) ;

        }   // end by-card newCardLike()


    /**
     * create a new card of the correct type for the specified value
     * <p>
     * this method breaks the general contract that a superclass doesn't know about its subclasses but this is
     * the natural place for it
     *
     * @param newColor
     *     color for the new card
     * @param newValue
     *     face value for the new card
     *
     * @return a new card of the type compatible with the kind of the value
     */
    public static Card newCardLike( final CardColor newColor,
                                    final CardValue newValue )
        {

        Objects.requireNonNull( newColor,
                                "newColor" ) ;
        Objects.requireNonNull( newValue,
                                "newValue" ) ;

        return switch ( newValue.getKind() )
            {

            case Kind.NUMBER -> new NumberCard( newColor,
                                                newValue ) ;
            case Kind.ACTION -> new ActionCard( newColor,
                                                newValue ) ;
            default -> throw new InternalError( String.format( "unrecognized Kind: %s",
                                                               newValue.getKind() ) ) ;

            } ;

        }   // end by-components newCardLike()


    /*
     * private utility methods
     */


    /**
     * convenience method to determine if comparisons of cards is disabled
     *
     * @return {@code true} if comparisons should not be done, {@code false} otherwise
     */
    private static boolean compareOnNone()
        {

        return Card.compareOnAttributes == CompareOn.COMPARE_NONE ;

        }   // end compareOnNone()


    /**
     * convenience method to determine if value should be considered when comparing two cards
     *
     * @return {@code true} if comparisons should consider value, {@code false} otherwise
     */
    private static boolean compareOnValue()
        {

        return ( Card.compareOnAttributes == CompareOn.COMPARE_VALUE_ONLY )
               || ( Card.compareOnAttributes == CompareOn.COMPARE_COLOR_AND_VALUE ) ;

        }   // end compareOnRank()


    /**
     * convenience method to determine if color should be considered when comparing two cards
     *
     * @return {@code true} if comparisons should consider color, {@code false} otherwise
     */
    private static boolean compareOnColor()
        {

        return ( Card.compareOnAttributes == CompareOn.COMPARE_COLOR_ONLY )
               || ( Card.compareOnAttributes == CompareOn.COMPARE_COLOR_AND_VALUE ) ;

        }   // end compareOnColor()

    }   // end class Card
