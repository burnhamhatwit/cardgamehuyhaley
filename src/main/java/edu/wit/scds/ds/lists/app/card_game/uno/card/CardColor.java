

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

import java.util.Arrays ;


/**
 * An enumeration of colors of cards in an Uno card deck.
 *
 * @author David M Rosenberg
 *
 * @version 1.0 2025-11-10 Initial implementation based upon {@code standard_cards.card.CardColor}
 */
public enum CardColor
    {

    // @formatter:off
      /** red card */
      RED ( "Red" )
    , /** yellow card */
      YELLOW ( "Yellow" )
    , /** green card */
      GREEN ( "Green" )
    , /** blue card */
      BLUE ( "Blue" )
    , /** for wildcard card */
      ANY ( "Any" )
    , /** unassigned color for wildcard action card */
      UNASSIGNED ( "Unassigned" )
    ;
    // @formatter:on

    /*
     * data fields
     */


    /** 'pretty' (capitalized) form of the color's name */
    private final String displayName ;

    /** substring for comparison in {@code interpretDescription()} */
    private final char matchOnCharacter ;


    /*
     * constructors
     */


    /**
     * set (initial) instance state
     *
     * @param colorDisplayName
     *     the 'pretty' (capitalized) form of the color's name
     */
    private CardColor( final String colorDisplayName )
        {

        this.displayName = colorDisplayName ;

        this.matchOnCharacter = Character.toLowerCase( colorDisplayName.charAt( 0 ) ) ;

        } // end constructor


    /*
     * public methods
     */


    /**
     * Returns the 'pretty' (capitalized) form of the color's name.
     *
     * @return the 'pretty' name of the color
     */
    public String getDisplayName()
        {

        return this.displayName ;

        } // end getDisplayName()


    /*
     * utility methods
     */


    /**
     * display the abbreviation for each value
     *
     * @since 2.0
     */
    public static void displayHelp()
        {

        System.out.printf( "%n%nTo specify a color, use its first letter (case insensitive):%n%n" ) ;

        Arrays.asList( CardColor.values() )
              .forEach( aCardColor ->
                  {

                  System.out.printf( "%2s -> \"%s\"%n",
                                     aCardColor.matchOnCharacter,
                                     aCardColor.displayName ) ;

                  } ) ;

        System.out.printf( "%n" ) ;

        }   // end displayHelp()


    /**
     * Parse a text description of card color
     *
     * @param cardColorDescription
     *     a name to parse
     *
     * @return the corresponding enum constant or null if the name is unrecognized
     */
    public static CardColor interpretDescription( final String cardColorDescription )
        {

        if ( ( cardColorDescription == null ) || ( cardColorDescription.length() < 1 ) )
            {

            return null ;   // nothing to parse

            }

        if ( "?".equals( cardColorDescription ) )
            {

            displayHelp() ;

            return null ;

            }

        final char forComparison = Character.toLowerCase( cardColorDescription.charAt( 0 ) ) ;

        for ( final CardColor aCardColor : CardColor.values() )
            {

            if ( aCardColor.matchOnCharacter == forComparison )
                {

                // found a match
                return aCardColor ;

                }

            }   // end for

        // no match
        return null ;

        }   // end method interpretDescription()


    @Override
    public String toString()
        {

        return getDisplayName() ;

        }    // end toString()


    /*
     * testing/debugging
     */


    /**
     * Test driver
     *
     * @param args
     *     -unused-
     */
    public static void main( final String[] args )
        {

        // display column headers
        System.out.printf( "  %-5s %-10s %-10s %-10s%n",
                           "#",
                           "Name",
                           "Color",
                           "Display Name" ) ;

        // display each element of the enumeration
        Arrays.asList( CardColor.values() )
              .forEach( aColor ->
                  {

                  System.out.printf( "  %-5d %-10s %-10s %c -> %-10s%n",
                                     aColor.ordinal(),
                                     aColor.name(),
                                     aColor,
                                     aColor.matchOnCharacter,
                                     aColor.displayName ) ;

                  } ) ;

        // display help
        displayHelp() ;

        }    // end main()

    } // end enum CardColor
