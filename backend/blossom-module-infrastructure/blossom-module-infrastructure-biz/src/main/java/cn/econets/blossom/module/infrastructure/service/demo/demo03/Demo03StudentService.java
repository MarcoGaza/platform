package cn.econets.blossom.module.infrastructure.service.demo.demo03;

import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.infrastructure.controller.admin.demo.demo03.vo.Demo03StudentPageReqVO;
import cn.econets.blossom.module.infrastructure.controller.admin.demo.demo03.vo.Demo03StudentSaveReqVO;
import cn.econets.blossom.module.infrastructure.dal.dataobject.demo.demo03.Demo03CourseDO;
import cn.econets.blossom.module.infrastructure.dal.dataobject.demo.demo03.Demo03GradeDO;
import cn.econets.blossom.module.infrastructure.dal.dataobject.demo.demo03.Demo03StudentDO;

import javax.validation.Valid;
import java.util.List;

/**
 * Student Service Interface
 *
 *
 */
public interface Demo03StudentService {

    /**
     * Create student
     *
     * @param createReqVO Create information
     * @return Number
     */
    Long createDemo03Student(@Valid Demo03StudentSaveReqVO createReqVO);

    /**
     * Update students
     *
     * @param updateReqVO Update information
     */
    void updateDemo03Student(@Valid Demo03StudentSaveReqVO updateReqVO);

    /**
     * Delete student
     *
     * @param id Number
     */
    void deleteDemo03Student(Long id);

    /**
     * Get students
     *
     * @param id Number
     * @return Student
     */
    Demo03StudentDO getDemo03Student(Long id);

    /**
     * Get student pages
     *
     * @param pageReqVO Paged query
     * @return Student Paging
     */
    PageResult<Demo03StudentDO> getDemo03StudentPage(Demo03StudentPageReqVO pageReqVO);


    // ==================== Subtable（Student Courses） ====================

    /**
     * Get student course list
     *
     * @param studentId Student Number
     * @return Student Course List
     */
    List<Demo03CourseDO> getDemo03CourseListByStudentId(Long studentId);

    /**
     * Get student course pages
     *
     * @param pageReqVO Paged query
     * @param studentId Student Number
     * @return Student Course Pagination
     */
    PageResult<Demo03CourseDO> getDemo03CoursePage(PageParam pageReqVO, Long studentId);

    /**
     * Create student courses
     *
     * @param demo03Course Create information
     * @return Number
     */
    Long createDemo03Course(@Valid Demo03CourseDO demo03Course);

    /**
     * Update student courses
     *
     * @param demo03Course Update information
     */
    void updateDemo03Course(@Valid Demo03CourseDO demo03Course);

    /**
     * Delete student courses
     *
     * @param id Number
     */
    void deleteDemo03Course(Long id);

    /**
     * Get student courses
     *
     * @param id Number
     * @return Student Courses
     */
    Demo03CourseDO getDemo03Course(Long id);

    // ==================== Subtable（Student Class） ====================

    /**
     * Get student class
     *
     * @param studentId Student Number
     * @return Student Class
     */
    Demo03GradeDO getDemo03GradeByStudentId(Long studentId);

    /**
     * Get student class pages
     *
     * @param pageReqVO Paged query
     * @param studentId Student Number
     * @return Student Class Page
     */
    PageResult<Demo03GradeDO> getDemo03GradePage(PageParam pageReqVO, Long studentId);

    /**
     * Create a student class
     *
     * @param demo03Grade Create information
     * @return Number
     */
    Long createDemo03Grade(@Valid Demo03GradeDO demo03Grade);

    /**
     * Update student classes
     *
     * @param demo03Grade Update information
     */
    void updateDemo03Grade(@Valid Demo03GradeDO demo03Grade);

    /**
     * Delete student class
     *
     * @param id Number
     */
    void deleteDemo03Grade(Long id);

    /**
     * Get student class
     *
     * @param id Number
     * @return Student Class
     */
    Demo03GradeDO getDemo03Grade(Long id);

}
