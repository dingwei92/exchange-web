package com.topsci.qh.webmanagement.Pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by lzw.
 * 16-6-28
 */
@Document
public class KafkaTopic {
    @Id
    private int id;
    private String topicName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KafkaTopic that = (KafkaTopic) o;

        if (id != that.id) return false;
        if (topicName != null ? !topicName.equals(that.topicName) : that.topicName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (topicName != null ? topicName.hashCode() : 0);
        return result;
    }
}
