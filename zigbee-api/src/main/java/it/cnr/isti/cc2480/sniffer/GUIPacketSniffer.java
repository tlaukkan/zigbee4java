/*
   Copyright 2008-2013 CNR-ISTI, http://isti.cnr.it
   Institute of Information Science and Technologies
   of the Italian National Research Council

   See the NOTICE file distributed with this work for additional
   information regarding copyright ownership

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package it.cnr.isti.cc2480.sniffer;

import com.itaca.ztool.api.ZToolPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;

/**
 * <b>NOTE:</bThe file saved by {@link FilePacketSniffer} cannot be opened by {@link GUIPacketSniffer}
 *
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 19:00:05 +0300 (Tue, 06 Aug 2013) $)
 * @since 0.1.0
 *
 */
public class GUIPacketSniffer
    extends javax.swing.JFrame
    implements SnifferInterface {

    private static final Logger logger = LoggerFactory.getLogger(GUIPacketSniffer.class);

    private static final SimplePacketFormat PACKET_FORMATTER = new SimplePacketFormat();

    private SnifferTableModel model;

    private javax.swing.JScrollPane jScrollPane1;

    private javax.swing.JTable jTable1;

    private javax.swing.JButton jbtLoad;

    private javax.swing.JButton jbtSave;

    private class SnifferFileFilter extends FileFilter {

        public String getDescription() {
            return "Serial Sniffer Files (*.sniffer)";
        }

        public boolean accept( File f ) {
            if (f.isDirectory()) {
                return true;
            } else if (f.getName().endsWith( ".snigger" )){
                return true;
            } else {
                return false;
            }
        }
    }

    private class LoadSaveListener implements ActionListener{

        public void actionPerformed( ActionEvent e ) {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new SnifferFileFilter());

            int returnVal = chooser.showOpenDialog(jScrollPane1);

            if(returnVal != JFileChooser.APPROVE_OPTION) {
                return;
            }

            if( e.getSource() == jbtLoad ) {
                loadFile(chooser.getSelectedFile());
            }else if( e.getSource() == jbtSave ) {
                saveFile(chooser.getSelectedFile());
            }
        }
    }


    private class SnifferTableModel
        extends AbstractTableModel {

        private final Object[] columns = new Object[] {
            new String[] {
                "Time", "Direction", "Type", "Packet", "Payload"
            }, new Class[] {
                Date.class, String.class, String.class, String.class, String.class
            }
        };

        private ArrayList<Object[]> rows = new ArrayList<Object[]>();

        public int getColumnCount() {
            return ( (String[]) columns[0] ).length;
        }

        public int getRowCount() {
            return rows.size();
        }

        public String getColumnName( int col ) {
            return ( (String[]) columns[0] )[col];
        }

        public Object getValueAt( int row, int col ) {
            return rows.get( row )[col];
        }

        public Class getColumnClass( int col ) {
            return ( (Class[]) columns[1] ).getClass();
        }

        public boolean isCellEditable( int row, int col ) {
            return false;
        }

        public void setValueAt( Object value, int row, int col ) {
            rows.get( row )[col] = value;
            fireTableCellUpdated( row, col );
        }

        public void addRow( Object[] values ) {
            rows.add( values );
            fireTableRowsInserted( rows.size() - 1, rows.size() - 1 );
        }

        public void clear() {
            int last = rows.size() - 1;
            rows.clear();
            fireTableRowsDeleted( 0, last );
        }
    }

    public GUIPacketSniffer() {
        initComponents();
    }

    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jbtLoad = new javax.swing.JButton();
        jbtSave = new javax.swing.JButton();

        setDefaultCloseOperation( javax.swing.WindowConstants.DISPOSE_ON_CLOSE );

        jTable1.setModel( getModel() );
        jScrollPane1.setViewportView( jTable1 );

        ActionListener loadSaveHandler = new LoadSaveListener();

        jbtLoad.setText( "Load" );
        jbtLoad.setFocusable( false );
        jbtLoad.setHorizontalTextPosition( javax.swing.SwingConstants.CENTER );
        jbtLoad.setVerticalTextPosition( javax.swing.SwingConstants.BOTTOM );
        jbtLoad.addActionListener( loadSaveHandler );

        jbtSave.setText( "Save" );
        jbtSave.setFocusable( false );
        jbtSave.setHorizontalTextPosition( javax.swing.SwingConstants.CENTER );
        jbtSave.setVerticalTextPosition( javax.swing.SwingConstants.BOTTOM );
        jbtSave.addActionListener( loadSaveHandler );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout( getContentPane() );
        getContentPane().setLayout( layout );
        layout.setHorizontalGroup( layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING ).addGroup(
            layout.createSequentialGroup().addContainerGap().addGroup(
                layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING ).addComponent( jScrollPane1,
                    javax.swing.GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE ).addGroup(
                    layout.createSequentialGroup().addComponent( jbtLoad ).addPreferredGap(
                        javax.swing.LayoutStyle.ComponentPlacement.RELATED, 387, Short.MAX_VALUE ).addComponent(
                        jbtSave ) ) ).addContainerGap() ) );
        layout.setVerticalGroup( layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING ).addGroup(
            layout.createSequentialGroup().addContainerGap().addGroup(
                layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING ).addComponent( jbtLoad,
                    javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE ).addComponent(
                    jbtSave, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE ) )
                .addPreferredGap( javax.swing.LayoutStyle.ComponentPlacement.RELATED ).addComponent( jScrollPane1,
                    javax.swing.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE ).addContainerGap() ) );
        pack();
    }

    protected void saveFile( File selectedFile ) {
        PrintStream out;
        try {
            out = new PrintStream(new FileOutputStream(selectedFile,true));
            out.println("#Dumping packets sniffed with the "+GUIPacketSniffer.class.getName());
        } catch (Exception e) {
            logger.debug("Failed to open file for dumping sniffed packet",e);
            return;
        }

        SnifferTableModel packets = getModel();
        int r = packets.getRowCount();
        int c = packets.getColumnCount();
        for ( int i = 0; i < r; i++ ) {
            StringBuffer sb = new StringBuffer();
            sb.append( ((Date) packets.getValueAt( i, 0 )).getTime() );
            for ( int j = 1; j < c; j++ ) {
                sb.append( ',' ).append( packets.getValueAt( i, j ) );
            }
            out.println(sb.toString());
        }
        out.flush();
        out.close();
     }

    protected void loadFile( File selectedFile ) {
        BufferedReader reader;
        try {
            reader = new BufferedReader( new FileReader( selectedFile ) );

            SnifferTableModel packets = getModel();
            packets.clear();
            String line;
            while ( ( line = reader.readLine() ) != null ) {
                if ( line.charAt( 0 ) == '#' ) continue;
                Object[] fields = line.split( "," );
                fields[0] = new Date( Long.parseLong( (String) fields[0] ) );
                packets.addRow( fields );
            }
        } catch ( IOException e ) {
            logger.debug("Failed to open file for reading sniffed packet",e);
            return;
        }

    }

    public SnifferTableModel getModel() {
        if ( model == null ) {
            model = new SnifferTableModel();
        }
        return model;
    }

    /**
     * @param args the command line arguments
     */
    public static void main( String args[] ) {
        java.awt.EventQueue.invokeLater( new Runnable() {

            public void run() {
                new GUIPacketSniffer().setVisible( true );
            }
        } );
    }

    public void finalize() {
        cleanUp();
    }

    private void cleanUp() {
        dispose();
    }

    public void incomingPacket( ZToolPacket p ) {
        Object[] data = new Object[5];
        data[0] = new Date( System.currentTimeMillis() );
        data[1] = "<-";
        data[2] = p.getCommandType().toString();
        data[3] = p.getClass().getName();
        data[4] = PACKET_FORMATTER.parsedFormat( p );
        getModel().addRow( data );
    }

    public void outcomingPacket( ZToolPacket p ) {
        Object[] data = new Object[5];
        data[0] = new Date( System.currentTimeMillis() );
        data[1] = "->";
        data[2] = p.getCommandType().toString();
        data[3] = p.getClass().getName();
        data[4] = PACKET_FORMATTER.parsedFormat( p );
        getModel().addRow( data );
    }

    public boolean initialize() {
        setVisible( true );
        return true;
    }

}
