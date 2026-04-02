

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
 * An enumeration of Uno card values
 *
 * @author David M Rosenberg
 *
 * @version 1.0 2025-11-10 Initial implementation based on {@code standard_cards.card.Rank}
 */
public enum CardValue
    {

// @formatter:off
//  Element     Display Name  Abbreviation  Graphic  Points  Sort Order  Quantity   Kind      Color(s)

    // number cards
    /** number card 0 */
    ZERO    (   "Zero",         "0",          "0",      0,       1,         1,   Kind.NUMBER, new CardColor[] { CardColor.RED, CardColor.YELLOW, CardColor.GREEN, CardColor.BLUE } )
    , /** number card 1 */
    ONE     (   "One",          "1",          "1",      1,       2,         2,   Kind.NUMBER, new CardColor[] { CardColor.RED, CardColor.YELLOW, CardColor.GREEN, CardColor.BLUE } )
    , /** number card 2 */
    TWO     (   "Two",          "2",          "2",      2,       3,         2,   Kind.NUMBER, new CardColor[] { CardColor.RED, CardColor.YELLOW, CardColor.GREEN, CardColor.BLUE } )
    , /** number card 3 */
    THREE   (   "Three",        "3",          "3",      3,       4,         2,   Kind.NUMBER, new CardColor[] { CardColor.RED, CardColor.YELLOW, CardColor.GREEN, CardColor.BLUE } )
    , /** number card 4 */
    FOUR    (   "Four",         "4",          "4",      4,       5,         2,   Kind.NUMBER, new CardColor[] { CardColor.RED, CardColor.YELLOW, CardColor.GREEN, CardColor.BLUE } )
    , /** number card 5 */
    FIVE    (   "Five",         "5",          "5",      5,       6,         2,   Kind.NUMBER, new CardColor[] { CardColor.RED, CardColor.YELLOW, CardColor.GREEN, CardColor.BLUE } )
    , /** number card 6 */
    SIX     (   "Six",          "6",          "6",      6,       7,         2,   Kind.NUMBER, new CardColor[] { CardColor.RED, CardColor.YELLOW, CardColor.GREEN, CardColor.BLUE } )
    , /** number card 7 */
    SEVEN   (   "Seven",        "7",          "7",      7,       8,         2,   Kind.NUMBER, new CardColor[] { CardColor.RED, CardColor.YELLOW, CardColor.GREEN, CardColor.BLUE } )
    , /** number card 8 */
    EIGHT   (   "Eight",        "8",          "8",      8,       9,         2,   Kind.NUMBER, new CardColor[] { CardColor.RED, CardColor.YELLOW, CardColor.GREEN, CardColor.BLUE } )
    , /** number card 9 */
    NINE    (   "Nine",         "9",          "9",      9,      10,         2,   Kind.NUMBER, new CardColor[] { CardColor.RED, CardColor.YELLOW, CardColor.GREEN, CardColor.BLUE } )

    // action cards
    , /** action card skip a turn */
    SKIP    (   "Skip",         "s",          "↷",     20,      11,         2,   Kind.ACTION, new CardColor[] { CardColor.RED, CardColor.YELLOW, CardColor.GREEN, CardColor.BLUE } )
    , /** action card reverse */
    REVERSE (   "Reverse",      "r",          "↩",     20,      12,         2,   Kind.ACTION, new CardColor[] { CardColor.RED, CardColor.YELLOW, CardColor.GREEN, CardColor.BLUE } )
    , /** action card draw 2 */
    DRAW_2  (   "Draw 2",       "t",         "+2",     20,      13,         2,   Kind.ACTION, new CardColor[] { CardColor.RED, CardColor.YELLOW, CardColor.GREEN, CardColor.BLUE } )
    , /** action card wild */
    WILD    (   "Wild",         "w",          "🎉",    50,      14,         4,   Kind.ACTION, new CardColor[] { CardColor.ANY } )
    , /** action card draw 4 */
    WILD_DRAW_4("Wild Draw 4",  "f",          "+4",    50,      15,         4,   Kind.ACTION, new CardColor[] { CardColor.ANY } )
    ;
// @formatter:on


    /*
     * constants
     */


    /** first number */
    public final static CardValue FIRST_NUMBER /* = ZERO */ ;

    /** last number */
    public final static CardValue LAST_NUMBER /* = NINE */ ;

    /** number of numbers */
    public final static int NUMBER_COUNT /*
                                          * = ( LAST_NUMBER.ordinal() - FIRST_NUMBER.ordinal() ) + 1
                                          */ ;

    /** first action */
    public final static CardValue FIRST_ACTION /* = SKIP */ ;

    /** last action */
    public final static CardValue LAST_ACTION /* = WILD_DRAW_4 */ ;

    /** number of actions */
    public final static int ACTION_COUNT /*
                                          * = ( LAST_ACTION.ordinal() - FIRST_ACTION.ordinal() ) + 1
                                          */ ;

    static
        {

        CardValue firstNumber = null ;
        CardValue lastNumber = null ;
        int numberOfNumberCards = 0 ;

        CardValue firstAction = null ;
        CardValue lastAction = null ;
        int numberOfActionCards = 0 ;

        // go through each CardValue remembering what we've seen
        for ( final CardValue aCardValue : CardValue.values() )
            {

            if ( aCardValue.kind == Kind.NUMBER )
                {

                if ( firstNumber == null )
                    {

                    firstNumber = aCardValue ;

                    }

                lastNumber = aCardValue ;
                numberOfNumberCards++ ;

                }
            else if ( aCardValue.kind == Kind.ACTION )
                {

                if ( firstAction == null )
                    {

                    firstAction = aCardValue ;

                    }

                lastAction = aCardValue ;
                numberOfActionCards++ ;

                }
            else
                {

                throw new IllegalStateException( String.format( "unrecognized Kind: %s",
                                                                aCardValue.kind ) ) ;

                }

            }

        // set the symbolic constants
        FIRST_NUMBER = firstNumber ;
        LAST_NUMBER = lastNumber ;
        NUMBER_COUNT = numberOfNumberCards ;

        FIRST_ACTION = firstAction ;
        LAST_ACTION = lastAction ;
        ACTION_COUNT = numberOfActionCards ;

        }   // end static initializer


    /*
     * static fields
     */
    // none


    /*
     * data fields
     */


    /** 'pretty' name for the value */
    private final String displayName ;

    /** abbreviated name for the value */
    private final String abbreviation ;

    /** graphic representation of the value */
    private final String graphic ;

    /** points for a card of this value for scoring */
    private final int points ;

    /** sort order */
    private final int sortOrder ;

    /** number per color */
    private final int quantity ;

    /** kind of card */
    private final Kind kind ;

    /** applicable colors */
    private final CardColor[] colors ;


    /*
     * constructor
     */


    /**
     * Sets all fields representing an Uno card's face value
     *
     * @param theDisplayName
     *     more esthetically pleasing for display
     * @param theAbbreviation
     *     abbreviation for the face value
     * @param theGraphic
     *     a graphic representation/icon
     * @param thePoints
     *     point value for the card for scoring
     * @param theSortOrder
     *     numeric sortOrder for the card
     * @param theQuantity
     *     the number of this value for each color
     * @param theKind
     *     number or action
     * @param theColors
     *     colors applicable for this value
     */
    private CardValue( final String theDisplayName,
                       final String theAbbreviation,
                       final String theGraphic,
                       final int thePoints,
                       final int theSortOrder,
                       final int theQuantity,
                       final Kind theKind,
                       final CardColor[] theColors )
        {

        this.displayName = theDisplayName ;
        this.abbreviation = theAbbreviation ;
        this.graphic = theGraphic ;
        this.points = thePoints ;
        this.sortOrder = theSortOrder ;
        this.quantity = theQuantity ;
        this.kind = theKind ;
        this.colors = theColors ;

        } // end constructor


    /*
     * public methods
     */


    /**
     * Retrieves the abbreviation
     *
     * @return the abbreviation
     */
    public String getAbbreviation()
        {

        return this.abbreviation ;

        }   // end getAbbreviation()


    /**
     * Retrieves the applicable color(s)
     *
     * @return the applicable colors
     */
    public CardColor[] getColors()
        {

        return this.colors ;

        } // end getColors()


    /**
     * Retrieves the display name
     *
     * @return the display name
     */
    public String getDisplayName()
        {

        return this.displayName ;

        } // end getDisplayName()


    /**
     * Retrieves the graphic/icon
     *
     * @return the graphic/icon
     */
    public String getGraphic()
        {

        return this.graphic ;

        } // end getGraphic()


    /**
     * Retrieves the kind of value
     *
     * @return the kind
     */
    public Kind getKind()
        {

        return this.kind ;

        } // end getKind()


    /**
     * Retrieves the point value
     *
     * @return the point value
     */
    public int getPoints()
        {

        return this.points ;

        } // end getPoints()


    /**
     * Retrieves the number of this value for each color
     *
     * @return the number of this value for each color
     */
    public int getQuantity()
        {

        return this.quantity ;

        } // end getQuantity()


    /**
     * Retrieves the sort order value
     *
     * @return the sortOrder
     */
    public int getSortOrder()
        {

        return this.sortOrder ;

        } // end getSortOrder()


    /*
     * utility methods
     */


    /**
     * display the abbreviation for each value
     */
    public static void displayHelp()
        {

        System.out.printf( "%n%nTo specify a value, use the abbreviation (case insensitive):%n%n" ) ;

        for ( final Kind aKind : Kind.values() )
            {

            System.out.printf( "%ss:%n",
                               aKind ) ;

            for ( final CardValue aValue : CardValue.values() )
                {

                if ( aValue.kind == aKind )
                    {

                    System.out.printf( "\t%2s -> %2s  %s%n",
                                       aValue.abbreviation,
                                       aValue.graphic,
                                       aValue.displayName ) ;

                    }

                }

            System.out.printf( "%n" ) ;

            }

        System.out.printf( "%n" ) ;

        }   // end displayHelp()


    /**
     * Parse a text description of a face value
     *
     * @param valueDescription
     *     a name to parse
     *
     * @return the corresponding enum constant or null if the name is unrecognized
     */
    public static CardValue interpretDescription( final String valueDescription )
        {

        if ( ( valueDescription == null ) || ( valueDescription.length() < 1 ) )
            {

            return null ;   // nothing to parse

            }

        if ( "?".equals( valueDescription ) )
            {

            displayHelp() ;

            return null ;

            }

        final String forComparison = valueDescription ;

        for ( final CardValue aValue : CardValue.values() )
            {

            if ( aValue.abbreviation.equalsIgnoreCase( forComparison ) )
                {

                // found a match
                return aValue ;

                }

            }

        // no match
        return null ;

        }   // end method interpretDescription()


    /**
     * For display, use the graphic/icon<br>
     * {@inheritDoc}
     */
    @Override
    public String toString()
        {

        return this.graphic ;

        }    // end toString()


    /*
     * for testing/debugging
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
        System.out.printf( " %-2s %-5s %-7s %-12s   %-12s %-15s %-6s  %-5s  %-8s   %-6s   %s%n",
                           "#",
                           "CardValue",
                           "Graphic",
                           "Abbreviation",
                           "Name",
                           "Display Name",
                           "Points",
                           "Order",
                           "# / Color",
                           "Kind",
                           "Color(s)" ) ;


        // display each element of the enumeration
        Arrays.asList( CardValue.values() )
              .forEach( aValue ->
                  {

                  System.out.printf( "%2d   %2s      %2s         %-6s   %-12s %-15s   %2d      %2d      %2d       %-6s   %s%n",
                                     aValue.ordinal(),
                                     aValue,
                                     aValue.graphic,
                                     aValue.abbreviation,
                                     aValue.name(),
                                     aValue.displayName,
                                     aValue.points,
                                     aValue.sortOrder,
                                     aValue.quantity,
                                     aValue.kind,
                                     Arrays.toString( aValue.colors ) ) ;

                  } ) ;    // end for

        System.out.printf( "%n%,d numbers: %s..%s; %,d actions: %s..%s%n",
                           NUMBER_COUNT,
                           FIRST_NUMBER,
                           LAST_NUMBER,
                           ACTION_COUNT,
                           FIRST_ACTION,
                           LAST_ACTION ) ;

        // display help
        displayHelp() ;

        }   // end main()


    /*
     * utility classes/enums
     */


    /**
     * tag for the kind of value
     */
    public enum Kind
        {

        // @formatter:off

          /** the value is a number */
        NUMBER( "Number" )

        , /** the value is an action */
        ACTION( "Action" )

        ;

        // @formatter:on


        /*
         * data fields
         */


        /** 'pretty' name for the value */
        private final String displayName ;


        /*
         * constructor
         */


        /**
         * Set the field(s) to their specified value(s)
         *
         * @param theDisplayName
         *     more esthetically pleasing for display
         */
        private Kind( final String theDisplayName )
            {

            this.displayName = theDisplayName ;

            }


        @Override
        public String toString()
            {

            return this.displayName ;

            }

        }   // end enum Kind

    } // end enum CardValue
