package com.a1ucard.reactive_redis_eg.serializer;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.io.*;

public class ObjectSerializer<T> implements RedisSerializer<T> {


    @Override
    public byte[] serialize(T t) throws SerializationException {

        if(t==null) {
            throw new NullPointerException();
        }

        try(
                ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
                ObjectOutputStream oos = new ObjectOutputStream(baos)
        ) {

            oos.writeObject(t);
            oos.flush();
            return baos.toByteArray();

        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to serialize object of type: "+ t.getClass(),e);
        }


    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {

        try(
                ByteArrayInputStream in = new ByteArrayInputStream(bytes);
                ObjectInputStream is = new ObjectInputStream(in)
        ) {
            return (T) is.readObject();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Failed to serialize object of type");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Class not found exception ");
        }
    }
}
