package org.texttechnologylab.data.impl.mongodb;


import org.texttechnologylab.data.ParliamentFactory;
import org.texttechnologylab.data.Speaker;
import org.texttechnologylab.data.impl.file.Fraction_File_Impl;

import java.util.Set;

public class Fraction_MongoDB_Impl extends Fraction_File_Impl {

    ParliamentFactory parliamentFactory = null;
    /**
     * Constructed based on a node.
     *
     * @param sName
     */
    public Fraction_MongoDB_Impl(ParliamentFactory pFactory, String sName) {
        super(sName);
        this.parliamentFactory = pFactory;
    }

    @Override
    public Set<Speaker> getMembers() {
        return this.parliamentFactory.getMembers(this);
    }


}
