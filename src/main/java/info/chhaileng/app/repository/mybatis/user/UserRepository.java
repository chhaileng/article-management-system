package info.chhaileng.app.repository.mybatis.user;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import info.chhaileng.app.model.security.Role;
import info.chhaileng.app.model.security.User;

@Repository
public interface UserRepository {
	
	@Select("SELECT id, fullname, username, password, status, photo FROM tb_user WHERE username=#{username}")
	@Results({
		@Result(property="id", column="id"),
		@Result(property="roles", column="id", many=@Many(select="findRoleByUserId"))
	})
	User loadUserByUsername(String username);
	
	@Select("SELECT id, fullname, username, password, status, photo FROM tb_user WHERE id=#{id}")
	@Results({
		@Result(property="id", column="id"),
		@Result(property="roles", column="id", many=@Many(select="findLimitRoleByUserId"))
	})
	User loadUserById(int id);
	
	@Select("SELECT r.id, r.role FROM tb_role r INNER JOIN tb_user_role ur ON r.id=ur.role_id WHERE ur.user_id=#{id}")
	List<Role> findRoleByUserId(int id);
	
	@Select("SELECT r.id, r.role FROM tb_role r INNER JOIN tb_user_role ur ON r.id=ur.role_id WHERE ur.user_id=#{id} ORDER BY r.id ASC LIMIT 1")
	List<Role> findLimitRoleByUserId(int id);
	
	@Select("SELECT id, fullname, username, password, status, photo FROM tb_user ORDER BY id ASC")
	@Results({
		@Result(property="id", column="id"),
		@Result(property="roles", column="id", many=@Many(select="findLimitRoleByUserId"))
	})
	List<User> findAllUsers();
	
	
	
	@Delete("DELETE FROM tb_user WHERE id = #{id}")
	boolean remove(int id);
	
	@Insert("INSERT INTO tb_user (fullname, username, password, status, photo) "
			+ "VALUES (#{fullname}, #{username}, #{password}, #{status}, #{photo})")
	boolean addUser(User user);
	
	@Insert("INSERT INTO tb_user_role (user_id, role_id) VALUES(#{user_id},#{role_id})")
	boolean addUserRole(@Param("user_id") int user_id, @Param("role_id") int role_id);
	
	@Update("UPDATE tb_user SET fullname=#{fullname}, photo=#{photo} WHERE id=#{id}")
	boolean updateInformation(User user);
	
	@Update("UPDATE tb_user SET password=#{password} WHERE id=#{id}")
	boolean updatePassword(User user);
	
}
