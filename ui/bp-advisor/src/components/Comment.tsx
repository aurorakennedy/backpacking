import React, { useState } from 'react';
import styles from './Comment.module.css';

interface CommentProps {
  author: string;
  content: string;
  onDelete: () => void;
  onUpdate: (updatedContent: string) => void;
  edited?: boolean;
  allowEditing?: boolean;
}

const Comment: React.FC<CommentProps> = ({ author, content, onDelete, onUpdate, edited = false, allowEditing = false }) => {
  const [showMenu, setShowMenu] = useState(false);
  const [showConfirmation, setShowConfirmation] = useState(false);
  const [isEditing, setIsEditing] = useState(false);
  const [updatedContent, setUpdatedContent] = useState(content);

  const handleDeleteClick = () => {
    setShowConfirmation(true);
  };

  const handleCancelClick = () => {
    setShowConfirmation(false);
    setShowMenu(false)
  };

  const handleConfirmClick = () => {
    setShowConfirmation(false);
    onDelete();
  };

  const handleEditClick = () => {
    setIsEditing(true);
    setShowMenu(false);
  };

  const handleSaveClick = () => {
    setIsEditing(false);
    onUpdate(updatedContent);
  };

  const handleCancelEditClick = () => {
    setIsEditing(false);
    setUpdatedContent(content);
  };

  const handleContentChange = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
    setUpdatedContent(event.target.value);
  };

  const handleMenuClick = () => {
    setShowMenu(!showMenu);
  };

  return (
    <div className={styles['comment-container']}>
      <div className={styles['comment-header']}>
        <h4 className={styles['comment-author']}>{author}</h4>
        {edited && <span className={styles['edited-tag']}>(Edited)</span>}
        {allowEditing && (
          <div className={styles['comment-menu']}>
            {showMenu && (
              <div className={styles['menu-dropdown']}>
                {!showConfirmation && (
                  <>
                    {!isEditing && <button className={styles['edit-button']} onClick={handleEditClick}>Edit</button>}
                    <button className={styles['delete-button']} onClick={handleDeleteClick}>Delete</button>
                  </>
                )}
                {showConfirmation && (
                  <>
                    <p className={styles['confirmation-message']}>Are you sure you want to delete this comment?</p>
                    <button className={styles['confirmation-cancel-button']} onClick={handleCancelClick}>Cancel</button>
                    <button className={styles['confirmation-confirm-button']} onClick={handleConfirmClick}>Delete Comment</button>
                  </>
                )}
              </div>
            )}
            <button className={styles['menu-button']} onClick={handleMenuClick}>
              <span className={styles['menu-icon']}>&#8942;</span>
            </button>
          </div>
        )}
      </div>
      <div className={styles['comment-body']}>
        {!isEditing && <p className={styles['comment-content']}>{updatedContent}</p>}
        {isEditing && (
          <textarea
            className={styles['comment-textarea']}
            value={updatedContent}
            onChange={handleContentChange}
          />
        )}
      </div>
      {isEditing && (
        <div className={styles['comment-footer']}>
          <button className={styles['save-button']} onClick={handleSaveClick}>Save</button>
          <button className={styles['cancel-button']} onClick={handleCancelEditClick}>Cancel</button>
        </div>
      )}
    </div>
  );
};

export default Comment;
