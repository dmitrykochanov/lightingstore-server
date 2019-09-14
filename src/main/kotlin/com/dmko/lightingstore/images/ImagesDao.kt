package com.dmko.lightingstore.images

import com.dmko.lightingstore.images.entities.ImageEntity
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select
import org.springframework.stereotype.Repository

@Mapper
@Repository
interface ImagesDao {

    @Insert("INSERT INTO images(id, data) VALUES(#{id}, #{data})")
    fun saveImage(image: ImageEntity)

    @Select("SELECT * FROM images WHERE id = #{id}")
    fun getImage(id: String): ImageEntity
}
