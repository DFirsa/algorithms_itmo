package com.company.Autumn.lab5;

import java.io.*;

public class MultiMap {

    static PrintWriter out;

    static int hash(String s, int tableSize){
        int str = 0;
        for (int i = 0; i < s.length(); i++){
            str = 31 * str + s.charAt(i);
        }
        str = Math.abs(str % tableSize);
        return str;
    }

    static class ListElement {
        ListElement next;
        ListElement prev;
        String data;

        public ListElement(String data){this.data = data;}
    }

    static class Set{
        ListElement[] table = new ListElement[230];
        Set next, prev;
        int count;
        String key;

        void insert(String value){
            if (exists(value)) return;
            int h = hash(value, table.length);
            ListElement a = table[h];
            if (table[h] == null){
                table[h] = new ListElement(value);
            }
            else{
                while (true){
                    if (a.next == null){
                        a.next = new ListElement(value);
                        a.next.prev = a;
                        break;
                    }
                    else a = a.next;
                }
            }
            count++;
        }

        void delete(String value){
            if (!exists(value)) return;
            int h = hash(value, table.length);
            ListElement a = table[h];
            while (a != null){
                if (a.data.compareTo(value) == 0){
                    if (a.next == null && a.prev == null){
                        table[h] = null;
                    }
                    else {
                        if (a.next == null){
                            a.prev.next = null;
                        }
                        else {
                            if (a.prev == null){
                                a.next.prev = null;
                                table[h] = a.next;
                            }
                            else{
                                a.next.prev = a.prev;
                                a.prev.next = a.next;
                            }
                        }
                    }
                    count--;
                    return;
                }
                else {
                    a = a.next;
                }
            }
        }

        boolean exists(String value){
            if (count <= 0) return false;

            int h = hash(value, table.length);
            ListElement a = table[h];
            if (table[h] == null) return false;
            while (a != null){
                if (a.data.compareTo(value) == 0) return true;
                a = a.next;
            }
            return false;
        }

        void get(){

            for (int i = 0; i < table.length; i++){
                ListElement a = table[i];
                while (a != null){
                    out.print(a.data + " ");
                    a = a.next;
                }
            }
            out.println();
        }
    }

    static class MultimapList {
        Set head;
        Set tail;

        void put(String key, String value){
            Set a = new Set();
            a.key = key;
            a.insert(value);
            if (tail == null){
                head = a;
                tail = a;
            }
            else{
                Set i;
                i = head;
                while (i != null){
                    if (i.key.compareTo(key) == 0){
                        i.insert(value);
                        return;
                    }
                    i = i.next;
                }

                tail.next = a;
                a.prev =  tail;
                tail = a;
            }
        }

        void deleteAll(String key){
            Set a;
            a = head;
            while (a != null){
                if (a.key.compareTo(key) == 0){
                    if (a.next == null && a.prev == null){
                        tail = null;
                        head = null;
                        return;
                    }
                    else{
                        if (a.next == null){
                            tail = a.prev;
                            a.prev.next = null;
                            return;
                        }
                        else{

                            if (a.prev == null){
                                a.next.prev = null;
                                head = a.next;
                                return;
                            }
                            else {
                                a.next.prev = a.prev;
                                a.prev.next = a.next;
                                return;
                            }
                        }
                    }
                }
                else {
                    a = a.next;
                }
            }
        }

        void delete(String key, String value){
            Set a;
            a = head;
            while (a != null){
                if (a.key.compareTo(key) == 0){
                    a.delete(value);
                    return;
                }
                else {
                    a = a.next;
                }
            }
        }

        void get(String key){
            Set a = head;
            while (a != null){
                if (key.compareTo(a.key) == 0) {
                    out.print(a.count + " ");
                    a.get();
                    return;
                }
                a = a.next;
            }
            out.println("0");
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File("multimap.in")));
        out = new PrintWriter("multimap.out");

        MultimapList[] multimap = new MultimapList[1000000];

        for (int i = 0; i < multimap.length; i++)multimap[i] = new MultimapList();

        String comand, key, value, line;

        int h;

        while ((line = reader.readLine()) != null){
            String[] in = line.split(" ");
            comand = in[0];
            key = in[1];
            h = hash(key, multimap.length);

            if (comand.compareTo("put") == 0){
                value = in[2];
                multimap[h].put(key, value);
            }

            if (comand.compareTo("get") == 0) multimap[h].get(key);

            if (comand.compareTo("delete") == 0){
                value = in[2];
                multimap[h].delete(key, value);
            }

            if (comand.compareTo("deleteall") == 0){
                multimap[h].deleteAll(key);
            }
        }

        reader.close();
        out.close();
    }
}

