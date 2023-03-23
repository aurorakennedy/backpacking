import React, { useState, useRef } from 'react';
import styles from './AddComment.module.css';

interface AddCommentProps {
  onSubmit: (comment: string) => void;
}

const AddComment: React.FC<AddCommentProps> = ({ onSubmit }) => {
  const [comment, setComment] = useState('');
  const textareaRef = useRef<HTMLTextAreaElement>(null);

  const handleChange = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
    setComment(event.target.value);

    if (textareaRef.current) {
      textareaRef.current.style.height = 'auto';
      textareaRef.current.style.height = `${textareaRef.current.scrollHeight}px`;
    }
  };

  const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    onSubmit(comment);
    setComment('');
  };

  const isCommentEmpty = comment.trim().length === 0;

  return (
    <form className={styles.addComment} onSubmit={handleSubmit}>
      <label>
        Add a comment:
        <textarea
          className={styles.commentTextarea}
          value={comment}
          onChange={handleChange}
          ref={textareaRef}
          style={{ height: '50px' }}
        />
      </label>
      <button
        className={styles.submitButton}
        type="submit"
        disabled={isCommentEmpty}
        style={{ backgroundColor: isCommentEmpty ? '#ccc' : undefined }}
      >
        Submit
      </button>
    </form>
  );
};

export default AddComment;
