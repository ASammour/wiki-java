/**
 *  @(#)WMFWikiUnitTest.java 0.01 12/11/2017
 *  Copyright (C) 2017 - 20xx MER-C
 *
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU General Public License
 *  as published by the Free Software Foundation; either version 3
 *  of the License, or (at your option) any later version. Additionally
 *  this file is subject to the "Classpath" exception.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software Foundation,
 *  Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */

package org.wikipedia;

import java.util.*;
import java.time.OffsetDateTime;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *  Unit tests for {@link org.wikipedia.WMFWiki}.
 *  @author MER-C
 */
public class WMFWikiTest
{
    private static WMFWiki enWiki;
    
    @BeforeClass
    public static void setUpClass()
    {
        enWiki = WMFWiki.createInstance("en.wikipedia.org");
        enWiki.setMaxLag(-1);
    }
    
    @Test
    public void isSpamBlacklisted() throws Exception
    {
        assertFalse(enWiki.isSpamBlacklisted("example.com"));
        assertTrue(enWiki.isSpamBlacklisted("youtu.be"));
    }
    
    @Test
    @Ignore
    // This is a semi-privileged action that requires the test runner to be not
    // blocked (even though login is not required). CI services (Travis,  
    // Codeship, etc) run on colocation facilities which are blocked as proxies,
    // causing the test to fail.
    public void getAbuseFilterLogs() throws Exception
    {
        // https://en.wikipedia.org/wiki/Special:AbuseLog?wpSearchUser=Miniapolis&wpSearchTitle=Catopsbaatar&wpSearchFilter=1
        List<Wiki.LogEntry> afl = enWiki.getAbuseLogEntries(Arrays.asList(1), "Miniapolis", "Catopsbaatar", 
            OffsetDateTime.parse("2018-04-05T00:00:00Z"), OffsetDateTime.parse("2018-04-06T01:00:00Z"));
        Wiki.LogEntry ale = afl.get(0);
        assertEquals("abuse log: id", 20838976L, ale.getID());
        assertEquals("abuse log: username", "Miniapolis", ale.getUser());
        assertEquals("abuse log: target", "Catopsbaatar", ale.getTarget());
        assertEquals("abuse log: timestamp", OffsetDateTime.parse("2018-04-05T22:58:14Z"), ale.getTimestamp());
        assertEquals("abuse log: action", "edit", ale.getAction());
        // TODO: test details when they are overhauled
    }
}
