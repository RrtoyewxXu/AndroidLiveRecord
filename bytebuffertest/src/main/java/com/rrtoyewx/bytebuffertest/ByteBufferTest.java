package com.rrtoyewx.bytebuffertest;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.Inet4Address;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * Created by Rrtoyewx on 16/9/7.
 * ByteBufferTest
 */
public class ByteBufferTest {

    public static void main(String[] args) {
        //flip
//        ByteBuffer heapByteBuffer = ByteBuffer.allocate(20);
//        printField("after create", heapByteBuffer);
//
//        for (int i = 0; i < 10; i++) {
//            heapByteBuffer.put((byte) i);
//        }
//        printField("after put", heapByteBuffer);
//
//        heapByteBuffer.flip();
//        printField("after flip", heapByteBuffer);
//
//        for (int i = 0; i < heapByteBuffer.limit(); i++) {
//            System.out.print(heapByteBuffer.get());
//        }
//        System.out.println();
//        printField("after get", heapByteBuffer);

//        rewind
//        ByteBuffer heapByteBuffer = ByteBuffer.allocate(20);
//        printField("after create", heapByteBuffer);
//
//        for (int i = 0; i < 10; i++) {
//            heapByteBuffer.put((byte) i);
//        }
//        printField("after put", heapByteBuffer);
//
//        heapByteBuffer.rewind();
//        printField("after rewind", heapByteBuffer);
//
//        for (int i = 0; i < heapByteBuffer.limit(); i++) {
//            System.out.print(heapByteBuffer.get());
//        }
//        System.out.println();
//        printField("after get", heapByteBuffer);

        //clear
//        ByteBuffer heapByteBuffer = ByteBuffer.allocate(20);
//        printField("after create", heapByteBuffer);
//
//        for (int i = 0; i < 10; i++) {
//            heapByteBuffer.put((byte) i);
//        }
//        printField("after put", heapByteBuffer);
//
//        heapByteBuffer.flip();
//        printField("after flip", heapByteBuffer);
//
//        for (int i = 0; i < heapByteBuffer.limit(); i++) {
//            System.out.print(heapByteBuffer.get());
//        }
//        System.out.println();
//        printField("after get", heapByteBuffer);
//
//        heapByteBuffer.clear();
//        printField("after clear",heapByteBuffer);
//
//        for (int i = 0; i < heapByteBuffer.limit(); i++) {
//            System.out.print(heapByteBuffer.get());
//        }
//        System.out.println();
//        printField("after get",heapByteBuffer);

        //mark reset
//        ByteBuffer heapByteBuffer = ByteBuffer.allocate(20);
//        printField("after create", heapByteBuffer);
//
//        for (int i = 0; i < 10; i++) {
//            heapByteBuffer.put((byte) i);
//        }
//        printField("after put", heapByteBuffer);
//
//        heapByteBuffer.mark();
//        printField("after mark", heapByteBuffer);
//
//        heapByteBuffer.position(15);
//        printField("after position", heapByteBuffer);
//        heapByteBuffer.reset();
//        printField("after reset", heapByteBuffer);

        //position limit
//        ByteBuffer heapByteBuffer = ByteBuffer.allocate(20);
//        printField("after create", heapByteBuffer);
//
//        heapByteBuffer.position(1);
//        printField("after position", heapByteBuffer);
//        heapByteBuffer.limit(10);
//        printField("after limit", heapByteBuffer);

        //remaining hasRemaining
//        ByteBuffer heapByteBuffer = ByteBuffer.allocate(20);
//        printField("after create", heapByteBuffer);
//
//        System.out.println("remaining:"+heapByteBuffer.remaining());
//        System.out.println("hasRemaining:"+heapByteBuffer.hasRemaining());

        //warp
//        ByteBuffer wrapByteBuffer = ByteBuffer.wrap(new byte[10]);
//        printField("after",wrapByteBuffer);

        //put(byte[]) get(byte[])
//        ByteBuffer heapByteBuffer = ByteBuffer.allocate(20);
//        byte[] buffer = new byte[]{
//                1, 2, 3, 4, 6, 7, 8, 9
//        };
//        heapByteBuffer.put(buffer);
//        printField("after put", heapByteBuffer);
//        heapByteBuffer.rewind();
//        printField("after rewind", heapByteBuffer);
//        byte[] outBuffer = new byte[20];
//        heapByteBuffer.get(outBuffer);
//        printField("after get", heapByteBuffer);
//        for(int i = 0;i < outBuffer.length;i++){
//            System.out.print(outBuffer[i]);
//        }


        //put(index),get(index)
//        ByteBuffer heapByteBuffer = ByteBuffer.allocate(20);
//        heapByteBuffer.put(2, (byte) 2);
//        printField("after put(index):", heapByteBuffer);
//        System.out.print(heapByteBuffer.get(2));
//        printField("after get(index):", heapByteBuffer);


        //slice
//        ByteBuffer heapByteBuffer = ByteBuffer.wrap(new byte[]{
//                0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19
//        });
//        printField("after warp", heapByteBuffer);
//        //mark = 0
//        heapByteBuffer.mark();
//        //position = 5;
//        heapByteBuffer.position(5);
//        //limit = 15;
//        heapByteBuffer.limit(15);
//        ByteBuffer slice = heapByteBuffer.slice();
//        printField("after slice", slice);
//        for (int i = 0; i < slice.limit(); i++) {
//            System.out.print(slice.get());
//        }
//        System.out.println();
//        //index = 7,value = 31;
//        slice.put(7, (byte) 31);
//        System.out.println(slice.get(7));
//        System.out.println(heapByteBuffer.get(12));

//        ByteBuffer heapByteBuffer = ByteBuffer.wrap(new byte[]{
//                0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19
//        });
//        printField("after warp", heapByteBuffer);
//        //mark = 0
//        heapByteBuffer.mark();
//        //position = 5;
//        heapByteBuffer.position(5);
//        //limit = 15;
//        heapByteBuffer.limit(15);
//
//        ByteBuffer duplicate = heapByteBuffer.duplicate();
//        printField("heapByteBuffer", heapByteBuffer);
//        printField("duplicate", duplicate);
//        duplicate.rewind();
//        for (int i = 0; i < duplicate.limit(); i++) {
//            System.out.print(duplicate.get());
//        }
//        System.out.println();
//        //index = 7,value = 31;
//        duplicate.put(7, (byte) 31);
//        System.out.println(duplicate.get(7));
//        System.out.println(heapByteBuffer.get(7));

//        ByteBuffer heapByteBuffer = ByteBuffer.wrap(new byte[]{
//                0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19
//        });
//        printField("after warp", heapByteBuffer);
//        //mark = 0
//        heapByteBuffer.mark();
//        //position = 5;
//        heapByteBuffer.position(5);
//        //limit = 15;
//        heapByteBuffer.limit(15);
//
//        byte[] array = heapByteBuffer.array();
//        printField("heapByteBuffer", heapByteBuffer);
//        for (int i = 0; i < array.length; i++) {
//            System.out.print(array[i]);
//        }
//        System.out.println();
//        //index = 7,value = 31;
//        array[7] = 31;
//        System.out.println(array[7]);
//        System.out.println(heapByteBuffer.get(7));

        //compact
//        ByteBuffer heapByteBuffer = ByteBuffer.wrap(new byte[]{
//                0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19
//        });
//        printField("after warp", heapByteBuffer);
//        //mark = 0
//        heapByteBuffer.mark();
//        //position = 5;
//        heapByteBuffer.position(5);
//        //limit = 15;
//        heapByteBuffer.limit(15);
//
//        ByteBuffer compact = heapByteBuffer.compact();
//        printField("heapByteBuffer", heapByteBuffer);
//        printField("compact", compact);
//        compact.rewind();
//        for (int i = 0; i < compact.limit(); i++) {
//            System.out.print(compact.get());
//        }
//        System.out.println();
//        //index = 7,value = 31;
//        compact.put(7, (byte) 31);
//        System.out.println(compact.get(7));
//        System.out.println(heapByteBuffer.get(7));

        //order
//        ByteBuffer heapByteBuffer = ByteBuffer.wrap(new byte[]{
//                0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19
//        });
//        IntBuffer intBuffer = heapByteBuffer.asIntBuffer();
//        printField("intBuffer", heapByteBuffer);
//        for (int i = 0; i < intBuffer.limit(); i++) {
//            System.out.println(intBuffer.get());
//        }
//        intBuffer.put(0,256);
//        for(int i = 0;i < heapByteBuffer.limit();i++){
//            System.out.print(heapByteBuffer.get());
//        }

        //file
//        File readFile = new File("bytebuffertest/read.txt");
//        File outFile = new File("bytebuffertest/write.txt");
//        try {
//            FileChannel readChannel = new FileInputStream(readFile).getChannel();
//            ByteBuffer readBuffer = ByteBuffer.allocate(1);
//            FileChannel outChannel = new FileOutputStream(outFile).getChannel();
//            while (readChannel.read(readBuffer) >= 0) {
//                readBuffer.flip();
//                System.out.print(Charset.forName("utf-8").decode(readBuffer));
//                readBuffer.flip();
//                outChannel.write(readBuffer);
//                readBuffer.clear();
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        //socket

    }

    private static void printField(String desc, ByteBuffer byteBuffer) {
        System.out.println(desc + "  capacity: " + byteBuffer.capacity() + " ,limit: " + byteBuffer.limit()
                + " ,position: " + byteBuffer.position() + " ,mark: " + mark(byteBuffer));
    }


    private static int mark(ByteBuffer byteBuffer) {
        try {
            Field mark = ByteBuffer.class.getSuperclass().getDeclaredField("mark");
            mark.setAccessible(true);
            return mark.getInt(byteBuffer);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return -2;
    }
}
