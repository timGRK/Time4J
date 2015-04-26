/*
 * -----------------------------------------------------------------------
 * Copyright © 2013-2015 Meno Hochschild, <http://www.menodata.de/>
 * -----------------------------------------------------------------------
 * This file (HistoricalIntegerElement.java) is part of project Time4J.
 *
 * Time4J is free software: You can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * Time4J is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Time4J. If not, see <http://www.gnu.org/licenses/>.
 * -----------------------------------------------------------------------
 */

package net.time4j.history;

import net.time4j.PlainDate;
import net.time4j.engine.BasicElement;
import net.time4j.engine.ChronoDisplay;
import net.time4j.engine.ChronoEntity;
import net.time4j.engine.Chronology;
import net.time4j.engine.ElementRule;
import net.time4j.format.NumericalElement;

import java.io.ObjectStreamException;


/**
 * <p>Allgemeines verstellbares chronologisches Element auf Integer-Basis. </p>
 *
 * @author      Meno Hochschild
 * @doctags.concurrency <immutable>
 */
final class HistoricalIntegerElement
    extends BasicElement<Integer>
    implements NumericalElement<Integer> {

    //~ Statische Felder/Initialisierungen --------------------------------

    /** Element-Index */
    static final int YEAR_OF_ERA = 2;

    //private static final long serialVersionUID = 1L;

    //~ Instanzvariablen --------------------------------------------------

    private transient final int index;
    private transient final Integer defaultMin;
    private transient final Integer defaultMax;
    private transient final char symbol;
    private transient final ElementRule<?, Integer> rule;

    //~ Konstruktoren -----------------------------------------------------

    HistoricalIntegerElement(
        String name,
        int index,
        Integer defaultMin,
        Integer defaultMax,
        char symbol,
        ElementRule<?, Integer> rule
    ) {
        super(name);

        this.index = index;
        this.defaultMin = defaultMin;
        this.defaultMax = defaultMax;
        this.symbol = symbol;
        this.rule = rule;

    }

    //~ Methoden ----------------------------------------------------------

    @Override
    public Class<Integer> getType() {

        return Integer.class;

    }

    @Override
    public char getSymbol() {

        return this.symbol;

    }

    @Override
    public int compare(
        ChronoDisplay o1,
        ChronoDisplay o2
    ) {

        return o1.get(this).compareTo(o2.get(this));

    }

    @Override
    public Integer getDefaultMinimum() {

        return this.defaultMin;

    }

    @Override
    public Integer getDefaultMaximum() {

        return this.defaultMax;

    }

    @Override
    public boolean isDateElement() {

        return true;

    }

    @Override
    public boolean isTimeElement() {

        return false;

    }

    @Override
    public int numerical(Integer value) {

        return value.intValue();

    }

    @Override
    @SuppressWarnings("unchecked")
    protected <T extends ChronoEntity<T>> ElementRule<T, Integer> derive(Chronology<T> chronology) {

        if (chronology.isRegistered(PlainDate.COMPONENT)) {
            return (ElementRule<T, Integer>) this.rule;
        }

        return null;

    }

    /**
     * <p>Liefert einen Zugriffsindex zur Optimierung der Elementsuche. </p>
     *
     * @return  int
     */
    int getIndex() {

        return this.index;

    }

    /**
     * <p>Erzeugt ein neues Datumselement. </p>
     *
     * @param   name        name of element
     * @param   index       index of element
     * @param   dmin        default minimum
     * @param   dmax        default maximum
     * @param   symbol      format symbol
     * @return  new element instance
     */
    static HistoricalIntegerElement create(
        String name,
        int index,
        int dmin,
        int dmax,
        char symbol
    ) {

        return new HistoricalIntegerElement(
            name,
            index,
            Integer.valueOf(dmin),
            Integer.valueOf(dmax),
            symbol,
            null
        );

    }

    private Object readResolve() throws ObjectStreamException {

        return this; // TODO: Use ChronoHistory-lookup

    }

}