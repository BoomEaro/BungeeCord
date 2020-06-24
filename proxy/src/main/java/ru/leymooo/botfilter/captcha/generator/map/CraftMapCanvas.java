package ru.leymooo.botfilter.captcha.generator.map;

import java.awt.Image;
import java.util.Arrays;

import ru.leymooo.botfilter.packets.MapDataPacket;

public class CraftMapCanvas
{

    private final byte[] buffer = new byte[ 16384 ];

    public CraftMapCanvas()
    {
        Arrays.fill( this.buffer, (byte) -1 );
    }

    public void setPixel(int x, int y, byte color)
    {
        if ( x >= 0 && y >= 0 && x < 128 && y < 128 )
        {
            if ( this.buffer[y * 128 + x] != color )
            {
                this.buffer[y * 128 + x] = color;
            }
        }
    }

    @SuppressWarnings("deprecation")
    public void drawImage(int x, int y, Image image)
    {
        byte[] bytes = MapPalette.imageToBytes( image );
        int width = image.getWidth( null );
        int height = image.getHeight( null );

        for ( int x2 = 0; x2 < width; ++x2 )
        {
            for ( int y2 = 0; y2 < height; ++y2 )
            {
                this.setPixel( x + x2, y + y2, bytes[y2 * width + x2] );
            }
        }

    }

    public MapDataPacket.MapData getMapData()
    {
        return new MapDataPacket.MapData( 128, 128, 0, 0, this.buffer );
    }
}
